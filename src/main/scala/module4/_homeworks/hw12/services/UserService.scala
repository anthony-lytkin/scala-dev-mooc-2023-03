package module4._homeworks.hw12.services

import io.getquill.context.ZioJdbc.QIO
import module4._homeworks.hw12.dao.entity.{Role, RoleCode, User, UserToRole}
import module4._homeworks.hw12.dao.repository.UserRepository
import module4._homeworks.hw12.dao.repository.UserRepository.ServiceImpl
import zio.{Has, RIO, Task, UIO, ZIO, ZLayer}
import zio.macros.accessible
import module4.phoneBook.db
import module4.phoneBook.db.DataSource

import java.sql.SQLException
import javax.sql

@accessible
object UserService {
  type UserService = Has[Service]

  trait Service {
    def listUsers(): RIO[db.DataSource, List[User]]

    def listUsersDTO(): RIO[db.DataSource, List[UserDTO]]

    def addUserWithRole(user: User, roleCode: RoleCode): RIO[db.DataSource, UserDTO]

    def listUsersWithRole(roleCode: RoleCode): RIO[db.DataSource, List[UserDTO]]
  }

  class Impl(userRepo: UserRepository.Service) extends Service {
    val dc = db.Ctx

    import dc._

    def listUsers(): RIO[db.DataSource, List[User]] = userRepo.list()

    def listUsersDTO(): RIO[db.DataSource, List[UserDTO]] = {
      for {
        users <- userRepo.list()
        roles <- for {
          roles <- ZIO.foreach(users)(user => userRepo.userRoles(user.typedId).map(_.toSet)).orDie
        } yield roles
        userWithRoles <- ZIO.succeed(users zip roles)
      } yield userWithRoles.map(user => UserDTO(user._1, user._2))


//      userRepo.list()
//        .flatMap {
//          users => ZIO.foreach(users)(user => ZIO.succeed(user) zip userRepo.userRoles(user.typedId))
//        }.map(_.map { user =>
//          UserDTO(user._1, user._2.toSet)
//        })
    }

    def addUserWithRole(user: User, roleCode: RoleCode): RIO[db.DataSource, UserDTO] = for {
      _ <- dc.transaction(
        for {
          _ <- userRepo.createUser(user)
          _ <- userRepo.createRole(Role(roleCode.code, roleCode.code.capitalize))
          _ <- userRepo.insertRoleToUser(roleCode, user.typedId)
        } yield ()
      )
      user  <- userRepo.findUser(user.typedId).some.mapError(_ => new Throwable(s"User ${user.id} not found."))
      roles <- userRepo.userRoles(user.typedId)
    } yield UserDTO(user, roles.toSet)

    def listUsersWithRole(roleCode: RoleCode): RIO[db.DataSource, List[UserDTO]] = for {
      users <- userRepo.listUsersWithRole(roleCode)
      roles <- for {
        roles <- ZIO.foreach(users)(user => userRepo.userRoles(user.typedId).map(_.toSet)).orDie
      } yield roles
      usersWithRoles <- ZIO.succeed(users zip roles)
    } yield usersWithRoles.map(user => UserDTO(user._1, user._2))

  }

  val live: ZLayer[UserRepository.UserRepository, Nothing, UserService] =
    ZLayer.fromService(userRepo => new Impl(userRepo))
}

case class UserDTO(user: User, roles: Set[Role])