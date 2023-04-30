import org.scalatest.funsuite.AnyFunSuite

import module1.homework1._
import module1.homework1.Option._
import module1.homework1.List._

class HW1TestModule extends AnyFunSuite {

  // Options
  val opt1: Option[Int] = Some(1)
  val opt2: Option[Int] = Some(2)
  val opt3: Option[Int] = None

  test("Test 1. Test Option .zip method") {
    assert((opt1 zip opt2) == Some(Some(1), Some(2)))
    assert((opt1 zip opt3) == Some(Some(1), None))
    assert((opt3 zip opt2) == Some(None, Some(2)))
    assert((opt3 zip opt3) == None)
  }

  test("Test 2. Test Option .filter method") {
    assert(opt1.filter(_ == 1) == Some(1))
    assert(opt1.filter(_ > 1) == None)
    assert(opt2.filter(_ <= 2) == Some(2))
    assert(opt3.filter(_ == 1) == None)
  }

  //List
  val list1: List[Int] = List(1, 2, 3, 4, 5)
  val list2: List[Int] = List(1)
  val list3: List[Int] = List.Nil

  test("Test 3. Test List .:: method") {
    assert(0 :: list1 == List(0, 1, 2, 3, 4, 5))
    assert(0 :: list2 == List(0, 1))
    assert(0 :: list3 == List(0))
  }

  test("Test 4. Test List .reverse method") {
    assert(list1.reverse == List(5, 4, 3, 2, 1))
    assert(list2.reverse == List(1))
    assert(list3.reverse == List.Nil)
  }

  test("Test 5. Test List .mkString method") {
    assert(list1.mkString(", ") == "1, 2, 3, 4, 5")
    assert(list1.mkString(" ") == "1 2 3 4 5")
    assert(list2.mkString(" ") == "1")
    assert(list3.mkString("---") == "")
  }

  test("Test 6. Test List .map method") {
    assert(list1.map(_.toString) == List("1", "2", "3", "4", "5"))
    assert(list1.map(x => x * 2) == List(2, 4, 6, 8, 10))
    assert(list2.map(_.toString) == List("1"))
    assert(list3.map(_.toString) == List.Nil)
  }

  test("Test 7. Test List .filter method") {
    assert(list1.filter(_ % 2 == 0) == List(2, 4))
    assert(list1.filter(_ % 2 == 1) == List(1, 3, 5))
    assert(list2.filter(_ % 2 == 0) == List.Nil)
    assert(list2.filter(_ % 2 == 1) == List(1))
    assert(list3.filter(_ % 2 == 0) == List.Nil)
  }

  test("Test 8. Test List .incList and .shoutString methods") {
    assert(List.incList(list1) == List(2, 3, 4, 5, 6))
    assert(List.incList(list2) == List(2))
    assert(List.incList(list3) == List.Nil)
    assert(List.shoutString(list1.map(_.toString)) == List("1!", "2!", "3!", "4!", "5!"))
    assert(List.shoutString(list2.map(_.toString)) == List("1!"))
    assert(List.shoutString(list3.map(_.toString)) == List.Nil)
  }

}
