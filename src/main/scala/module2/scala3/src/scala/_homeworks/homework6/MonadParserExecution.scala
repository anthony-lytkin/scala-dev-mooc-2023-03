package _homeworks.homework6

import _homeworks.homework6.parser._

object MonadParserExecution {

  case class Car(year: Int, mark: String, model: String, comment: String, price: Float) {
    override def toString: String =
      s"""Car
         |Year:    $year
         |Mark:    $mark
         |Model:   $model
         |Comment: $comment
         |Price:   $price
         |""".stripMargin
  }

  @main def execute(): Unit = {

    val raw = "1997;Ford;E350;ac, abs, moon;3000\n1996; Jeep; Grand Cherokee; MUST SELL! air, moon roof, loaded; 4799"
    val splitedRaw = raw.split("\n")

    def convertField[T](using converter: FieldConversion[String, T]): MonadParser[T, String] = {
      MonadParser.stringMonadParser.map(s => converter.convert(s.trim))
    }

    val parser = for {
      year    <- convertField[Int]
      mark    <- convertField[String]
      model   <- convertField[String]
      comment <- convertField[String]
      price   <- convertField[Float]
    } yield Car(year, mark, model, comment, price)

    val parsed = splitedRaw.map(parser.parse)

    parsed.foreach { println }

  }

}
