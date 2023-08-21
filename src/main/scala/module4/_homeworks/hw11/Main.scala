package module4._homeworks.hw11

import HttpApp.httpApp
import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {

  override def run: IO[Unit] = server run httpApp

}
