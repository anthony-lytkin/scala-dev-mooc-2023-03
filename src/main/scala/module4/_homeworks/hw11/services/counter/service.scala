package module4._homeworks.hw11.services.counter

import cats.effect.{Deferred, FiberIO, IO, Ref, Resource, Spawn}
import module4._homeworks.hw11.dto.CounterDTO

object service {

  private val counter: IO[Counter] = Counter.initializeIO.
    uncancelable

  // Подскажите, потратил достаточно много времени, но почему у меня здесь не происходит обновления счетчика
  // Буду благодарен за наводку, задачу переделаю
  def response: IO[CounterDTO] = counter.flatMap(_.state.getAndUpdate(_ + 1)).map(CounterDTO.apply)


}
