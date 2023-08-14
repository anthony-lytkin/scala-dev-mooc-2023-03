package module3._homeworks.hw10

import cats.effect.Sync
import cats.implicits._
import module3._homeworks.hw10.Wallet._

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths, StandardOpenOption}

// DSL управления электронным кошельком
trait Wallet[F[_]] {
  // возвращает текущий баланс
  def balance: F[BigDecimal]

  // пополняет баланс на указанную сумму
  def topup(amount: BigDecimal): F[Unit]

  // списывает указанную сумму с баланса (ошибка если средств недостаточно)
  def withdraw(amount: BigDecimal): F[Either[WalletError, Unit]]
}

// Игрушечный кошелек который сохраняет свой баланс в файл
// todo: реализовать используя java.nio.file._
// Насчёт безопасного конкуррентного доступа и производительности не заморачиваемся, делаем максимально простую рабочую имплементацию. (Подсказка - можно читать и сохранять файл на каждую операцию).
// Важно аккуратно и правильно завернуть в IO все возможные побочные эффекты.
//
// функции которые пригодятся:
// - java.nio.file.Files.write
// - java.nio.file.Files.readString
// - java.nio.file.Files.exists
// - java.nio.file.Paths.get
final class FileWallet[F[_] : Sync](id: WalletId) extends Wallet[F] {

  private object config {
    val STORAGE_PATH: Path = Wallet.walletStoragePath(id)
    val UTF8 = StandardCharsets.UTF_8
    val TRUNCATE = StandardOpenOption.TRUNCATE_EXISTING
  }

  private object Utils {
    def unit: F[Unit] = Sync[F].unit
  }

  private object FileActions {

    import config._
    import Utils._

    def read: F[String] = Sync[F].delay(Files.readAllLines(STORAGE_PATH, UTF8).toArray.mkString)
    def write[T](value: T): F[Unit] = Sync[F].delay(Files.write(STORAGE_PATH, value.toString.getBytes(UTF8), TRUNCATE)) >> unit
  }

  import FileActions._

  // MAIN ACTIONS

  private def readBalance: F[BigDecimal] = Sync[F].map(read)(BigDecimal(_))

  private def writeBalance(amount: BigDecimal): F[Unit] = write(amount)

  override def balance: F[BigDecimal] = readBalance

  override def topup(amount: BigDecimal): F[Unit] = Sync[F].flatMap(readBalance)(b => writeBalance(b + amount))

  override def withdraw(amount: BigDecimal): F[Either[WalletError, Unit]] = Sync[F].flatMap(readBalance) { b =>
    if (b >= amount) Sync[F].map(writeBalance(b - amount))(Right(_)) else Sync[F].pure(Left(BalanceTooLow))
  }

}

object Wallet {

  // todo: реализовать конструктор
  // внимание на сигнатуру результата - инициализация кошелька имеет сайд-эффекты
  // Здесь нужно использовать обобщенную версию уже пройденного вами метода IO.delay,
  // вызывается она так: Sync[F].delay(...)
  // Тайпкласс Sync из cats-effect описывает возможность заворачивания сайд-эффектов

  type WalletId = String

  def walletStoragePath(id: WalletId): Path = {
    Paths.get(System.getProperty("user.dir") + s"/src/main/scala/module3/_homeworks/hw10/wallets/" +
      s"wallet-$id.txt")
  }

  def fileWallet[F[_] : Sync](id: WalletId): F[Wallet[F]] = {
    val storagePath = walletStoragePath(id)
    val initialBalance = BigDecimal(0)

    Sync[F].blocking(Files.exists(storagePath)).flatMap { f =>
      if (f) Sync[F].unit else {
        Sync[F].delay(Files.createFile(storagePath)) *>
          Sync[F].delay(Files.write(storagePath, initialBalance.toString().getBytes)) *>
          Sync[F].unit
      }
    } *> Sync[F].delay(new FileWallet[F](id))
  }

  sealed trait WalletError

  case object BalanceTooLow extends WalletError

  case object NegativeAmount extends WalletError
}
