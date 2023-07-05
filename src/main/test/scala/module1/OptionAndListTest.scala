package scala.module1

import module1._homeworks.homework1
import org.scalatest.funsuite.AnyFunSuite

class OptionAndListTest extends AnyFunSuite {

  // Options
  val opt1: homework1.Option[Int] = homework1.Option.Some(1)
  val opt2: homework1.Option[Int] = homework1.Option.Some(2)
  val opt3: homework1.Option[Int] = homework1.Option.None

  test("Test 1. Test Option .zip method") {
    assert((opt1 zip opt2) == homework1.Option.Some(homework1.Option.Some(1), homework1.Option.Some(2)))
    assert((opt1 zip opt3) == homework1.Option.Some(homework1.Option.Some(1), homework1.Option.None))
    assert((opt3 zip opt2) == homework1.Option.Some(homework1.Option.None, homework1.Option.Some(2)))
    assert((opt3 zip opt3) == homework1.Option.None)
  }

  test("Test 2. Test Option .filter method") {
    assert(opt1.filter(_ == 1) == homework1.Option.Some(1))
    assert(opt1.filter(_ > 1) == homework1.Option.None)
    assert(opt2.filter(_ <= 2) == homework1.Option.Some(2))
    assert(opt3.filter(_ == 1) == homework1.Option.None)
  }

  //List
  val list1: homework1.List[Int] = homework1.List(1, 2, 3, 4, 5)
  val list2: homework1.List[Int] = homework1.List(1)
  val list3: homework1.List[Int] = homework1.List.Nil

  test("Test 3. Test List .:: method") {
    assert(0 :: list1 == homework1.List(0, 1, 2, 3, 4, 5))
    assert(0 :: list2 == homework1.List(0, 1))
    assert(0 :: list3 == homework1.List(0))
  }

  test("Test 4. Test List .reverse method") {
    assert(list1.reverse == homework1.List(5, 4, 3, 2, 1))
    assert(list2.reverse == homework1.List(1))
    assert(list3.reverse == homework1.List.Nil)
  }

  test("Test 5. Test List .mkString method") {
    assert(list1.mkString(", ") == "1, 2, 3, 4, 5")
    assert(list1.mkString(" ") == "1 2 3 4 5")
    assert(list2.mkString(" ") == "1")
    assert(list3.mkString("---") == "")
  }

  test("Test 6. Test List .map method") {
    assert(list1.map(_.toString) == homework1.List("1", "2", "3", "4", "5"))
    assert(list1.map(x => x * 2) == homework1.List(2, 4, 6, 8, 10))
    assert(list2.map(_.toString) == homework1.List("1"))
    assert(list3.map(_.toString) == homework1.List.Nil)
  }

  test("Test 7. Test List .filter method") {
    assert(list1.filter(_ % 2 == 0) == homework1.List(2, 4))
    assert(list1.filter(_ % 2 == 1) == homework1.List(1, 3, 5))
    assert(list2.filter(_ % 2 == 0) == homework1.List.Nil)
    assert(list2.filter(_ % 2 == 1) == homework1.List(1))
    assert(list3.filter(_ % 2 == 0) == homework1.List.Nil)
  }

  test("Test 8. Test List .incList and .shoutString methods") {
    assert(homework1.List.incList(list1) == homework1.List(2, 3, 4, 5, 6))
    assert(homework1.List.incList(list2) == homework1.List(2))
    assert(homework1.List.incList(list3) == homework1.List.Nil)
    assert(homework1.List.shoutString(list1.map(_.toString)) == homework1.List("1!", "2!", "3!", "4!", "5!"))
    assert(homework1.List.shoutString(list2.map(_.toString)) == homework1.List("1!"))
    assert(homework1.List.shoutString(list3.map(_.toString)) == homework1.List.Nil)
  }

}
