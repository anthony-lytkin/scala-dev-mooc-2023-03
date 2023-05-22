package module2.homeworks.homework5

import scala.language.implicitConversions

object TupleF {

  def tuple[A, B](a: List[A], b: List[B]): List[(A, B)] =
    a.flatMap { a => b.map((a, _)) }

  def tuple[A, B](a: Option[A], b: Option[B]): Option[(A, B)] =
    a.flatMap { a => b.map((a, _)) }

  def tuple[E, A, B](a: Either[E, A], b: Either[E, B]): Either[E, (A, B)] =
    a.flatMap { a => b.map((a, _)) }

  def tupleF[F[_], A, B](fa: F[A], fb: F[B])
                        (implicit bindableA: F[A] => BindableF[F, A], bindableB: F[B] => BindableF[F, B]): F[(A, B)] = {

    fa.flatMap(a => fb.map((a, _)))
  }


}
