package actions

import org.scalatest.matchers.must.Matchers.AWord

trait BaseTestAction {

  def testAction: List[AWord]

  def doTests = testAction.foreach { println }

}
