package _homeworks.homework7

object IntExtension {

  opaque type StringExt = String

  object StringExt {
    def apply(s: String): StringExt = s
  }

  extension (x: StringExt)
    private def toInt(s: StringExt) = s.toInt // Просто для эксперимента
    def :+(y: StringExt): Int = toInt(x + y)
    // Метод "+" уже определен y Int и там проблема с компиляцией

  @main def main(): Unit = {
    val a: StringExt = "56"
    val b: StringExt = "3"
    val c: Int = a :+ b
    assert(c == 563)
    println(c)
  }

}
