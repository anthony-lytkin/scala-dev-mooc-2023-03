import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


class MainTest extends AnyFunSuite with Matchers {

  val testAction: BaseTestAction = HW1TestAction

  val a = test("Test") {
    assert(1 == 1)
  }


}
