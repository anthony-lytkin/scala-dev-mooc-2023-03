package module4._homeworks.hw11.services.counter

import cats.effect.{IO, Ref}

private[counter] case class Counter(state: Ref[IO, BigInt])

object Counter {

  def initializeIO: IO[Counter] = Ref.of[IO, BigInt](1).map(Counter.apply)

}
