package module2._homeworks.homework5

import scala.language.implicitConversions

trait BindableF[F[_], A]{

  def map[B](f: A => B): F[B]
  def flatMap[B](f: A => F[B]): F[B]

}

object BindableF {

  implicit class BinderOption[A](a: Option[A]) extends BindableF[Option, A] {
    override def map[B](f: A => B): Option[B] = a.map(f)
    override def flatMap[B](f: A => Option[B]): Option[B] = a.flatMap(f)
  }

  implicit class BindList[A](a: List[A]) extends BindableF[List, A] {
    override def map[B](f: A => B): List[B] = a.map(f)
    override def flatMap[B](f: A => List[B]): List[B] = a.flatMap(f)
  }

}

