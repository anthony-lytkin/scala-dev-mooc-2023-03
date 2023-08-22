package module4._homeworks.hw11

import cats.data.{Kleisli, ReaderT}
import cats.effect.{IO, Resource}
import com.comcast.ip4s.{Host, Port}
import org.http4s.ember.server.EmberServerBuilder
import config._
import org.http4s.{HttpApp, HttpRoutes, Request, Response}
import org.http4s.server.Server

package object server {

  def ember(service: ReaderT[IO, Request[IO], Response[IO]]): Resource[IO, Server] = EmberServerBuilder
    .default[IO]
    .withHost(Host.fromString(HOST).get)
    .withPort(Port.fromInt(PORT).get)
    .withHttpApp(service)
    .build

  def run(service: ReaderT[IO, Request[IO], Response[IO]]): IO[Unit] = ember(service).use(_ => IO.never)

}
