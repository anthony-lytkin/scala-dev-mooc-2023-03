package module3._homeworks.hw10

import cats.effect.{IO, IOApp}
import cats.implicits._

import scala.concurrent.duration.DurationInt

// Поиграемся с кошельками на файлах и файберами.

// Нужно написать программу где инициализируются три разных кошелька и для каждого из них работает фоновый процесс,
// который регулярно пополняет кошелек на 100 рублей раз в определенный промежуток времени. Промежуток надо сделать разный, чтобы легче было наблюдать разницу.
// Для определенности: первый кошелек пополняем раз в 100ms, второй каждые 500ms и третий каждые 2000ms.
// Помимо этих трёх фоновых процессов (подсказка - это файберы), нужен четвертый, который раз в одну секунду будет выводить балансы всех трех кошельков в консоль.
// Основной процесс программы должен просто ждать ввода пользователя (IO.readline) и завершить программу (включая все фоновые процессы) когда ввод будет получен.
// Итого у нас 5 процессов: 3 фоновых процесса регулярного пополнения кошельков, 1 фоновый процесс регулярного вывода балансов на экран и 1 основной процесс просто ждущий ввода пользователя.

// Можно делать всё на IO, tagless final тут не нужен.

// Подсказка: чтобы сделать бесконечный цикл на IO достаточно сделать рекурсивный вызов через flatMap:
// def loop(): IO[Unit] = IO.println("hello").flatMap(_ => loop())
object WalletFibersApp extends IOApp.Simple {

  val STORAGE_WRITE_ERROR = "Can not write to storage"

  def printErrorIO(msg: String)(e: Throwable): IO[Unit] = IO.println(s"$msg. Reason: ${e.toString}")
  def printIO(msg: String): IO[Unit] = IO.println(msg)

  def run: IO[Unit] =
    for {
      _ <- IO.println("Press any key to stop...")
      wallet1 <- Wallet.fileWallet[IO]("1")
      wallet2 <- Wallet.fileWallet[IO]("2")
      wallet3 <- Wallet.fileWallet[IO]("3")
      f1 <- (wallet1.topup(100).handleErrorWith(printErrorIO(STORAGE_WRITE_ERROR)(_)) >> IO.sleep(100.millis)).foreverM.start
      f2 <- (wallet2.topup(100).handleErrorWith(printErrorIO(STORAGE_WRITE_ERROR)(_)) >> IO.sleep(500.millis)).foreverM.start
      f3 <- (wallet3.topup(100).handleErrorWith(printErrorIO(STORAGE_WRITE_ERROR)(_)) >> IO.sleep(2000.millis)).foreverM.start
      _ <- (IO.sleep(1.second) *> wallet1.balance.flatMap(b => printIO(s"Balance wallet1: $b"))).foreverM.start
      _ <- (IO.sleep(1.second) *> wallet2.balance.flatMap(b => printIO(s"Balance Wallet2: $b"))).foreverM.start
      _ <- (IO.sleep(1.second) *> wallet3.balance.flatMap(b => printIO(s"Balance Wallet3: $b"))).foreverM.start
      _ <- IO.readLine >> f1.cancel >> f2.cancel >> f3.cancel
    } yield ()

}
