package module4._homeworks.hw12.dao.entity

case class Role(code: String, name: String)

case class User(id: String, firstName: String, lastName: String, age: Int) {
  def typedId: UserId = UserId(id)
}

case class UserToRole(roleId: String, userId: String)

case class RoleCode(code: String) extends AnyVal

case class UserId(id: String) extends AnyVal