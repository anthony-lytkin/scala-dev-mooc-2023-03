//package module4._homeworks.hw11.intro
//
//import cats.effect.IO
//
//import scala.concurrent.Future
//import scala.io.StdIn
//
//object IOIntro {
//  object Constructors {
//    import cats.effect.IO
//
//    // 1. Чистое значение
//    val a = IO.pure(42)
//
//    // 2. Сайд-эффект
//    def side: IO[Unit] = IO.delay {
//      val in = StdIn.readLine()
//      println(in)
//    }
//
//    // 3. Умные конструкторы
//    def f: Future[Int] = ???
//    def either: IO[Int] = IO.fromFuture(IO.delay(f))
//  }
//
//
//  object Combinators {
//    // 1. Последовательное вычисление
//    def double: IO[Unit] = Constructors.side *> Constructors.side
//  }
//}
