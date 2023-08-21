package module3._homeworks.hw9

import module3._homeworks.hw9.zio_homework._
import module3.zioRecursion
import module3.zioRecursion.readIntOrRetry
import zio.clock.Clock
import zio.console.Console
import zio.random.Random
import zio.{ExitCode, UIO, URIO, ZIO}

object ZioHomeWorkApp extends zio.App {
  override def run(args: List[String]): URIO[zio.ZEnv with Console, ExitCode] = {
    runApp.exitCode
  }
}
