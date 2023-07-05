package scala.module2

import module2._homeworks.homework5.TupleF.tupleF
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.{List, Nil}

class TupleFTest extends AnyFlatSpec {

  val opt1: Option[Int]     = Some(1)
  val opt2: Option[Boolean] = Some(true)
  val opt3: Option[String]  = Some("3")
  val opt4: Option[String]  = None

  val list1: List[Int]        =  1 :: 2 :: 3 :: Nil
  val list2: List[String]     = "1" :: "2" :: "3" :: Nil
  val list3: List[(Int, Int)] = (1, 2) :: Nil
  val list4: List[List[Int]]  = Nil

  "Options TupleF" should "ok" in {
    assert(tupleF(opt1, opt2) == Option((1, true)))
    assert(tupleF(opt2, opt3) == Option((true, "3")))
    assert(tupleF(opt3, opt4).isEmpty)
  }

  "Lists TupleF" should "ok" in {
    assert(tupleF(list1, list2) == List((1, "1"), (1, "2"), (1, "3"), (2, "1"), (2, "2"), (2, "3"), (3, "1"), (3, "2"), (3, "3")))
    assert(tupleF(list2, list3) == List(("1", (1, 2)), ("2", (1, 2)), ("3", (1, 2))))
    assert(tupleF(list3, list4).isEmpty)
  }

}

