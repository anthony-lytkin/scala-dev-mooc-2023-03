package module4._homeworks.hw12.dao.repository

import io.getquill.{EntityQuery, Query, Quoted}
import zio.{Has, ULayer, ZIO, ZLayer}
import io.getquill.context.ZioJdbc._
import module4._homeworks.hw12.dao.entity.{Role, RoleCode, User, UserId, UserToRole}
import zio.macros.accessible
import module4.phoneBook.db

import java.sql.SQLException
import javax.sql.DataSource


object UserRepository {


  val dc = db.Ctx

  import dc._

  type UserRepository = Has[Service]

  trait Service {
    def findUser(userId: UserId): QIO[Option[User]]
    def createUser(user: User): QIO[User]
    def createUsers(users: List[User]): QIO[List[User]]
    def updateUser(user: User): QIO[Unit]
    def deleteUser(user: User): QIO[Unit]
    def findByLastName(lastName: String): QIO[List[User]]
    def list(): QIO[List[User]]
    def userRoles(userId: UserId): QIO[List[Role]]
    def createRole(role: Role): QIO[Role]
    def insertRoleToUser(roleCode: RoleCode, userId: UserId): QIO[Unit]
    def listUsersWithRole(roleCode: RoleCode): QIO[List[User]]
    def findRoleByCode(roleCode: RoleCode): QIO[Option[Role]]
  }

  class ServiceImpl extends Service {

    private lazy val userSchema: Quoted[EntityQuery[User]] = quote(querySchema[User](""""User""""))
    private lazy val roleSchema: Quoted[EntityQuery[Role]] = quote(querySchema[Role](""""Role""""))
    private lazy val userToRoleSchema: Quoted[EntityQuery[UserToRole]] = quote(querySchema[UserToRole](""""UserToRole""""))

    def findUser(userId: UserId): Result[Option[User]] = run(userSchema.filter(_.id == lift(userId.id)))
      .map(_.headOption)

    def createUser(user: User): Result[User] = run(userSchema.insert(lift(user))).as(user)

    def createUsers(users: List[User]): Result[List[User]] = run(liftQuery(users).foreach(u => query.insert(u))).as(users)

    def updateUser(user: User): Result[Unit] = run(userSchema.filter(_.id == lift(user.id)).update(lift(user))).unit

    def deleteUser(user: User): Result[Unit] = run(userSchema.filter(_.id == lift(user.id)).delete).unit

    def findByLastName(lastName: String): Result[List[User]] = run(userSchema.filter(_.lastName == lift(lastName)))

    def list(): Result[List[User]] = run(userSchema)

    def userRoles(userId: UserId): Result[List[Role]] = run(
      for {
        user <- userSchema.filter(_.id == lift(userId.id))
        us2r <- userToRoleSchema.join(_.userId == user.id)
        role <- roleSchema.join(_.code == us2r.roleId)
      } yield role
    )

    def createRole(role: Role): QIO[Role] = run(roleSchema.insert(lift(role))).as(role)

    def insertRoleToUser(roleCode: RoleCode, userId: UserId): Result[Unit] = run(userToRoleSchema.insert(
      lift(UserToRole(roleCode.code, userId.id)))
    ).unit

    def listUsersWithRole(roleCode: RoleCode): Result[List[User]] = run(
      for {
//        user <- userSchema.filter(_ => 1 == 1)
//        _ <- userToRoleSchema.join(_.userId == user.id).filter(_.roleId == lift(roleCode.code))
        roles <- userToRoleSchema.filter(_.roleId == lift(roleCode.code))
        users <- userSchema.join(_.id == roles.userId)
      } yield users
    )

    def findRoleByCode(roleCode: RoleCode): Result[Option[Role]] = run(roleSchema.filter(_.code == lift(roleCode.code)))
      .map(_.headOption)
  }

  val live: ULayer[UserRepository] = ZLayer.succeed(new ServiceImpl)
}