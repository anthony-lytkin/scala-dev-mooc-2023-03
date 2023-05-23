package _homeworks.homework7

object Logarithm {

  opaque type Logarithm = Double

  object Logarithm {
    def apply(d: Double) = math.log(d)
  }

  extension (x: Logarithm)
    def toDouble = math.exp(x)
    def +(y: Logarithm) = Logarithm(math.exp(x) + math.exp(y))
    def *(y: Logarithm) = x + y

  @main def mainLogarithm(): Unit = {
    val log1 = Logarithm(math.E)
    val log2 = Logarithm(1)

    val log3 = log1 + log2
    val log4 = log1 * log2
    val log5 = log1 * log1 + log2

    assert(log3 == 1)
    assert(log4 == 0)
    assert(log5 == 1)
  }

}
