package module1

object functions {


  /**
   * Функции
   */

  class Foo{
    def sum(x: Int, y: Int): Int = x + y
  }

  val foo2: (Int, Int) => Int = new Foo().sum _

  /**
   * Реализовать ф-цию  sum, которая будет суммировать 2 целых числа и выдавать результат
   */

   def sum(x: Int, y: Int): Int = x + y

   val r1: Int = sum(2, 3) // 5

  val sum2: (Int, Int) => Int = (a, b) => a + b

   sum(2, 3)  // 5
   sum2(2, 3) // 5

  val sum3: (Int, Int) => Int = sum


  val evenCondition: Int => Boolean = (_: Int) % 2 == 0

  sum3(2, 3) // 5
  List(sum2, sum3)

  // Partial function

  val divide: PartialFunction[(Int, Int), Int] = {
    case x if x._2 != 0 => x._1 / x._2
  }

//  divide.isDefinedAt()
//  divide.apply()

  val divide2: PartialFunction[(Int, Int), Int] = new PartialFunction[(Int, Int), Int] {
    override def isDefinedAt(x: (Int, Int)): Boolean = x._2 != 0

    override def apply(v1: (Int, Int)): Int = v1._1 / v1._2
  }
//  divide2.isDefinedAt()

  val r5: List[Int] = List((1, 2), (2, 0)).collect(divide)

  divide.isDefinedAt((10, 0)) // false
  divide.isDefinedAt((10, 5)) // true

  val sumCurried: Int => Int => Int = sum2.curried

  val r6: Int => Int = sumCurried(5)
  r6(3) // 8


  // SAM Single Abstract Method

  trait Printer{
    def apply(str: String): Unit

  }

  val p: Printer = s => println(s)

  p.apply("hello")


  /**
   *  Задание 1. Написать ф-цию метод isEven, которая будет вычислять является ли число четным
   */

  def isEven(x: Int): Boolean = if (x % 2 == 0) true else false


  /**
   * Задание 2. Написать ф-цию метод isOdd, которая будет вычислять является ли число нечетным
   */

  def isOdd(x: Int): Boolean = if (x % 2 == 1) true else false
  def isOdd1(x: Int): Boolean = !isEven(x)

  /**
   * Задание 3. Написать ф-цию метод filterEven, которая получает на вход массив чисел и возвращает массив тех из них,
   * которые являются четными
   */

  val lst: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
  def filterEven(lst: List[Int]): List[Int] = lst.filter(isEven)
  def filterEven1(lst: List[Int]): List[Int] = lst.collect { case n if isEven(n) => n }



  /**
   * Задание 4. Написать ф-цию метод filterOdd, которая получает на вход массив чисел и возвращает массив тех из них,
   * которые являются нечетными
   */

  def filterOdd(lst: List[Int]): List[Int] = lst.collect { case n if isOdd(n) => n }


  /**
   * return statement
   *
   */
}