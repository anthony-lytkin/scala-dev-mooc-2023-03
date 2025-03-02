package module2._homeworks.homework8

trait Semigroup[A] {
  def combine(x: A, y: A): A
}


object Semigroup {


  def apply[A](implicit ev: Semigroup[A]): Semigroup[A] = ev

  implicit val semigroupInt = new Semigroup[Int] {
    override def combine(x: Int, y: Int): Int = x + y
  }

  implicit val semigroupString = new Semigroup[String] {
    override def combine(x: String, y: String): String = x ++ y
  }

  def double[A: Semigroup](a: A): A =
    Semigroup[A].combine(a, a)

  double("foo")

}
