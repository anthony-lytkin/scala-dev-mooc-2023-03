package module2._homeworks.homework8

trait Monad[F[_]] extends Functor[F] { self =>

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = flatten(map(fa)(a => f(a)))

  def point[A](a: A): F[A]

  def flatten[A](fa: F[F[A]]): F[A]
}

object Monad {

  implicit def monadOption[T]: Monad[Option] = new Monad[Option] { self =>

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)

    override def point[A](a: A): Option[A] = Option(a)

    override def flatten[A](fa: Option[Option[A]]): Option[A] = fa.flatten
  }

  implicit def monadList[T]: Monad[List] = new Monad[List] {

    override def point[A](a: A): List[A] = List(a)

    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)

    override def flatten[A](fa: List[List[A]]): List[A] = fa.flatten
  }

  import scala.util.Try
  implicit def monadTry: Monad[Try] = new Monad[Try] {

    override def point[A](a: A): Try[A] = Try(a)

    override def map[A, B](fa: Try[A])(f: A => B): Try[B] = fa.map(f)

    override def flatten[A](fa: Try[Try[A]]): Try[A] = fa.flatten
  }

}
