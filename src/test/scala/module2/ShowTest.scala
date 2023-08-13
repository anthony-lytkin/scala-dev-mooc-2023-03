package scala.module2

import module2._homeworks.homework8.Show
import org.scalatest.flatspec.AnyFlatSpec

class ShowTest extends AnyFlatSpec {

  val listInt: List[Int] = 1 :: 2 :: 3 :: 4 :: 5 :: Nil
  val listString: List[String] = "1" :: "2" :: "3" :: "4" :: "5" :: Nil
  val listBoolean: List[Boolean] = true :: false :: true :: Nil
  val listSingle: List[Int] = 1 :: Nil
  val listEmpty: List[String] = Nil

  "mkString_" should "ok" in {
    assert(Show.mkString_(listInt, "", "", ";") == "1;2;3;4;5")
    assert(Show.mkString_(listString, "[", "]", " ") == "[1 2 3 4 5]")
    assert(Show.mkString_(listBoolean, "", "", ", ") == "true, false, true")
    assert(Show.mkString_(listSingle, "", "", "") == "1")
    assert(Show.mkString_(listEmpty, "[", "]", "") == "[]")
  }

}
