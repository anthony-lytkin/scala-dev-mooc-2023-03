package module3._homeworks.hw9

import module3.zioConcurrency.printEffectRunningTime
import module3.zio_homework.config._
import zio.ZIO.{replicate, succeed, yieldNow}
import zio.clock.Clock
import zio.config.ReadError
import zio.config.typesafe.TypesafeConfig
import zio.console.Console
import zio.duration.durationInt
import zio.random.Random
import zio.{Has, IO, Layer, Task, UIO, ULayer, URIO, ZIO, ZLayer}

import java.util.concurrent.TimeUnit
import scala.language.postfixOps

package object zio_homework {

  /**
   * 1.
   * Используя сервисы Random и Console, напишите консольную ZIO программу которая будет предлагать пользователю угадать число от 1 до 3
   * и печатать в консоль угадал или нет. Подумайте, на какие наиболее простые эффекты ее можно декомпозировать.
   */
  lazy val guessProgram: URIO[Random with Console, Unit] = {

    val randomN: URIO[Random, Int] = zio.random.nextIntBetween(1, 4)

    val readConsole: URIO[Console, String] = zio.console.getStrLn.orDie

    def writeToConsole(msg: String): URIO[Console, Unit] = zio.console.putStrLn(msg)

    val consoleInputToInt: ZIO[Console, Throwable, Int] = readConsole.flatMap(n => ZIO.effect(n.toInt)) // Throwable намеренно ликвидирован

    lazy val userInputAttempt: URIO[Console, Int] = consoleInputToInt orElse (writeToConsole("Your number is incorrect. Try again") zipRight userInputAttempt)

    val userGuess: ZIO[Console, Throwable, Int] = writeToConsole("Your guess:") zipRight userInputAttempt

    val app: ZIO[Console with Random, Throwable, Unit] = for {
      n <- randomN
      _ <- writeToConsole("Try to guess number, input integer in range [1, 3]: ")
      _ <- doWhile(userGuess)(_ != n)
      _ <- writeToConsole("You are guessed!")
    } yield ()
    // Тут какой-то косяк, введенное число может не совпасть, в то же время пишет что угадал (обратной ситуации
    // не возникаоло вроде), почему - не понимаю :(

    app.orDie
  }

  /**
   * 2. реализовать функцию doWhile (общего назначения), которая будет выполнять эффект до тех пор, пока его значение в условии не даст true
   *
   */
  def doWhile[R, E, A](effect: => ZIO[R, E, A])(condition: A => Boolean): ZIO[R, E, A] = effect.repeatUntil(condition)

  def doWhileM[R, R1 <: R, E, A](effect: => ZIO[R, E, A])(condition: A => URIO[R1, Boolean]): ZIO[R1, E, A] = {
    effect.repeatUntilM(condition)
  }

  /**
   * 3. Реализовать метод, который безопасно прочитает конфиг из файла, а в случае ошибки вернет дефолтный конфиг
   * и выведет его в консоль
   * Используйте эффект "load" из пакета config
   */


  def loadConfigOrDefaut: UIO[AppConfig] = {

    class DefaultAppConfig(override val host: String, override val port: String) extends AppConfig(host, port) {
      override def toString: String = "Default " + super.toString
    }

    object DefaultAppConfig {

      def apply(host: String, port: String) = new DefaultAppConfig(host, port)

      def useDefault: DefaultAppConfig = {
        val (defaultHost, defaultPort) = ("localhost", "8080")
        apply(defaultHost, defaultPort)
      }
    }

    val config: UIO[AppConfig] = load.foldM(
      e => ZIO.succeed(println(e.getMessage)) zipRight ZIO.succeed(DefaultAppConfig.useDefault),
      c => ZIO.succeed(c.api)
    )

    config
  }


  /**
   * 4. Следуйте инструкциям ниже для написания 2-х ZIO программ,
   * обратите внимание на сигнатуры эффектов, которые будут у вас получаться
   * на изменение этих сигнатур
   */


  /**
   * 4.1 Создайте эффект, который будет возвращать случайеым образом выбранное число от 0 до 10 спустя 1 секунду
   * Используйте сервис zio Random
   */
  lazy val eff: URIO[Random with Clock, Int] = for {
    _ <- ZIO.sleep(1 seconds)
    r <- ZIO.environment[Random].map(_.get).flatMap(_.nextIntBetween(0, 11))
  } yield r

  /**
   * 4.2 Создайте коллекцию из 10 выше описанных эффектов (eff)
   */
  lazy val effects: Iterable[URIO[Random with Clock, Int]] = replicate(10)(eff)

  /**
   * 4.3 Напишите программу которая вычислит сумму элементов коллекци "effects",
   * напечатает ее в консоль и вернет результат, а также залогирует затраченное время на выполнение,
   * можно использовать ф-цию printEffectRunningTime, которую мы разработали на занятиях
   */
  lazy val app: URIO[Random with Clock, Int] = printEffectRunningTime(ZIO.foreach(effects)(identity).map(_.sum))

  /**
   * 4.4 Усовершенствуйте программу 4.3 так, чтобы минимизировать время ее выполнения
   */
  lazy val appSpeedUp: URIO[Random with Clock, Int] = printEffectRunningTime(ZIO.foreachPar(effects)(identity).map(_.sum))

  /**
   * 5. Оформите ф-цию printEffectRunningTime разработанную на занятиях в отдельный сервис, так чтобы ее
   * молжно было использовать аналогично zio.console.putStrLn например
   */

  type ConsoleLog = Has[ConsoleLog.Service]

  object ConsoleLog {

    // Не знаю куда поместить, пусть будут здесь
    private def currentTime: URIO[Clock, Long] = zio.clock.currentTime(TimeUnit.MILLISECONDS)

    private object result {
      sealed trait Result

      case object Succeed extends Result
      case object Failed  extends Result
    }

    trait Service {
      def printEffectRunningTime[R, E, A](effect: => ZIO[R, E, A]): URIO[ConsoleLog with Clock with R, Unit]
    }

    object Service {
      val live: Service = new Service {
        override def printEffectRunningTime[R, E, A](effect: => ZIO[R, E, A]): URIO[ConsoleLog with Clock with R, Unit] = for {
          start <- currentTime
          result <- effect.fold(_ => result.Failed, _ => result.Succeed)
          end <- currentTime
          //        _ <- zio.console.putStrLn(s"Running time: ${end - start} ms. Result: $result.")
          _ <- ZIO.succeed(println(s"Running time: ${end - start} ms. Result: $result."))
        } yield ()
      }
    }

    val live: ULayer[Has[Service]] = ZLayer.succeed(Service.live)

    def printEffectRunningTime[R, E, A](effect: => ZIO[R, E, A]): URIO[ConsoleLog with Clock with R, Unit] = {
      ZIO.accessM(_.get.printEffectRunningTime(effect))
    }

  }


  /**
   * 6.
   * Воспользуйтесь написанным сервисом, чтобы созадть эффект, который будет логировать время выполнения прогаммы из пункта 4.3
   *
   *
   */

  lazy val appWithTimeLog: URIO[ConsoleLog with Clock with Random, Unit] = ConsoleLog.printEffectRunningTime(appSpeedUp)

  /**
   *
   * Подготовьте его к запуску и затем запустите воспользовавшись ZioHomeWorkApp
   */

  lazy val runApp: URIO[zio.ZEnv, Unit] = {
    val app: URIO[ConsoleLog with Clock with Random, Unit] = ConsoleLog.printEffectRunningTime(appWithTimeLog)
    app.provideCustomLayer(Random.live ++ ConsoleLog.live)
    // У меня тут что-то не так с окружением (не удалось добиться ConsoleLog with Clock, избавившись от Random)
    // так и не понял почему, хотелось бы тут уточнить. То есть я планировал вернуть URIO[ConsoleLog with Clock, Unit],
    // вместо этого окружение криво собиралось, что пришлось затянуть его только через provideCustomLayer,
    // что дает аж ZEnv, насколько понял там вся простыня из местных сервисов. Но это работает)
  }

}
