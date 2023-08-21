package module4._homeworks.hw11

import module4._homeworks.hw11.requests.ChunkRequest
import services.counter.{service => counterService}
import services.chunk.{service => chunkService}

import cats.data.ReaderT
import cats.effect.IO

import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.{MediaType, Request, Response}
import org.http4s.dsl.io._
import org.http4s.headers.`Content-Type`


object HttpApp extends Router {

  val httpApp: ReaderT[IO, Request[IO], Response[IO]] = routes {
    case GET -> Root / "check"   => Ok("Status Ok.")
    case GET -> Root / "counter" => Ok(counterService.response)
    case GET -> Root / "slow" / chunk / total / time =>
      ChunkRequest.fromUrlParams(chunk, total, time)
        .fold(BadRequest("Invalid URL params")) { r =>
          val result = chunkService.readStream(r)
          Ok(result , `Content-Type`(MediaType.text.plain))
        }
  }.orNotFound

}
