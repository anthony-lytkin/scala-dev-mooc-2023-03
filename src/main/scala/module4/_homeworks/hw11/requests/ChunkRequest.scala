package module4._homeworks.hw11.requests

import scala.util.Try

case class ChunkRequest(chunk: Int, total: Int, time: Long) {
  require(chunk > 0 && total > 0 && time > 0)
}

object ChunkRequest {

  def fromUrlParams(chunk: String, total: String, time: String): Option[ChunkRequest] = Try {
    val chunkInt = chunk.toInt
    val totalInt = total.toInt
    val timeLong = time.toLong
    ChunkRequest(chunkInt, totalInt, timeLong)
  }.toOption

}
