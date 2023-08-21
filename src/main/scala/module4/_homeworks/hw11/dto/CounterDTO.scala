package module4._homeworks.hw11.dto

import io.circe.Encoder
import io.circe.derivation.deriveEncoder

case class CounterDTO(counter: BigInt)

object CounterDTO {

  implicit val serializer: Encoder.AsObject[CounterDTO] = deriveEncoder

}
