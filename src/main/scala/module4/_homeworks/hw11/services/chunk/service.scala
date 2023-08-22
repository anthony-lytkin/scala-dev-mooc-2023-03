package module4._homeworks.hw11.services.chunk

import module4._homeworks.hw11.requests.ChunkRequest
import module4._homeworks.hw11.services.chunk.Storage._

import fs2._
import fs2.io.file._
import cats.effect.IO

import java.nio.charset.StandardCharsets
import scala.concurrent.duration._

object service {

  def readStream(request: ChunkRequest): Stream[IO, String] = {
    val fileStream: Stream[IO, Char] = Files[IO].readAll(PATH, request.total)
      .through(text.decodeWithCharset(StandardCharsets.UTF_8))
      .flatMap(Stream.emits(_))

    fileStream
      .take(request.total)
      .groupWithin(request.chunk, request.time.seconds)
      .evalMap { ch =>
        val s = ch.toCharBuffer.toString
        IO.println(s"Streaming content: $s") *>
          IO.delay(s)
      }
      .zipLeft(Stream.awakeEvery[IO](request.time.seconds))
  }

}
