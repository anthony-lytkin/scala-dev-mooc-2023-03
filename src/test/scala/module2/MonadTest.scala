package scala.module2

import module2._homeworks.homework8.Monad
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.{Success, Try}

class MonadTest extends AnyFlatSpec {

  val testMonadOption: Monad[Option] = Monad.monadOption
  val testMonadList: Monad[List] = Monad.monadList[Int]
  val testMonadTry: Monad[Try] = Monad.monadTry

  val testValue = "test"
  val testValueOpt: Option[String] = Some(testValue)

  val testList = 1 :: 2 :: 3 :: 4 :: 5 :: Nil

  val testTry: Try[Boolean] = Try { true }
  val successTry = Success(true)

  "Monad[Option]" should "ok" in {
    assert(testMonadOption.point(testValueOpt) == Some(testValueOpt))
    assert(testMonadOption.flatMap(testValueOpt)(x => Some(x.toUpperCase)) == Some("TEST"))
  }

  "Monad[List]" should "ok" in {
    assert(testMonadList.point(testList) == List(testList))
    assert(testMonadList.flatMap(testList)(x => List(x + 1)) == List(2, 3, 4, 5, 6))
  }

  "Monad[Try]" should "ok" in {
    assert(testMonadTry.point(testTry) == Success(successTry))
    assert(testMonadTry.flatMap(testTry)(x => Try { x.toString }) == Try { "true" })
  }
}
