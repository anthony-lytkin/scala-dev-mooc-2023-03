package module4._homeworks.hw11

import cats.effect.IO
import org.http4s.{HttpRoutes, Request, Response}

trait Router {

  def routes(request2Response: PartialFunction[Request[IO], IO[Response[IO]]]): HttpRoutes[IO] =
    HttpRoutes.of(request2Response)

}
