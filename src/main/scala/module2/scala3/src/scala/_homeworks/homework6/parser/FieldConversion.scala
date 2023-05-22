package _homeworks.homework6.parser

trait FieldConversion[A, B] {

  def convert(x: A): B

}

object FieldConversion {
  
  given StringFieldConversion: FieldConversion[String, String] with 
    override def convert(x: String): String = x
  
  given IntFieldConversion: FieldConversion[String, Int] with
    override def convert(x: String): Int = x.toInt

  given FloatFieldConversion: FieldConversion[String, Float] with
    override def convert(x: String): Float = x.toFloat

  given BooleanFieldConversion: FieldConversion[String, Boolean] with
    override def convert(x: String): Boolean = x.toBoolean 
}
