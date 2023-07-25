package module2._homeworks.homework8

trait Order[A] {
  def compare(x: A, y: A): Option[Boolean]
}


