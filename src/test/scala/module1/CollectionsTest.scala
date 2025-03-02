package scala.module1

import module1._homeworks.homework3.TaskCollections._
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.{List, Seq, Set, Vector}


/**
 * Homework 3
 */
class CollectionsTest extends AnyFlatSpec {

  "check capitalizeIgnoringASCII" should "ok" in {
    assert(capitalizeIgnoringASCII(List("Lorem", "ipsum", "dolor", "sit", "amet")) == List("Lorem", "IPSUM", "DOLOR", "SIT", "AMET"))
    assert(capitalizeIgnoringASCII(List("Оказывается", ",", "ЗвУк", "КЛАВИШЬ")) === List("Оказывается", ",", "звук", "клавишь"))
  }

  "check numbersToNumericString" should "ok" in {
    val text = "Hello. I am 9 years old"
    val transformText = "Hello. I am nine years old"
    assert(numbersToNumericString(text) === transformText)
    assert(numbersToNumericString("") === "")
    assert(numbersToNumericString("4") === "four")
  }

  "check intersectionAuto" should "ok" in {
    val dealerOne = Vector(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    val dealerTwo = Seq(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    assert(intersectionAuto(dealerOne, dealerTwo) === Set(Auto("BMW", "i3"), Auto("Mazda", "X5")))
  }

  "check filterAllLeftDealerAutoWithoutRight" should "ok" in {
    val dealerOne = Vector(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    val dealerTwo = Seq(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    assert(filterAllLeftDealerAutoWithoutRight(dealerOne, dealerTwo) === Set.empty)

    val dealerOneSecond = Vector(Auto("BMW", "i3"), Auto("Mazda", "X5"))
    val dealerTwoSecond = Seq(Auto("BMW", "i3"))
    assert(filterAllLeftDealerAutoWithoutRight(dealerOneSecond, dealerTwoSecond) === Set(Auto("Mazda", "X5")))
  }

}
