package module1._homeworks.homework2

import scala.math.Ordering.Boolean

object BallsTest extends App {

  val countWhiteBalls = 3
  val countBlackBalls = 3

  val count = 1000000
  val listOfExperiments: List[BallExperiment] = List.fill(count)(new BallExperiment(countWhiteBalls, countBlackBalls))
  val countOfPositiveExperiments: Int = listOfExperiments.foldLeft(0)(_ + _.isFirstBlackSecondWhite.compare(false))
  val probability: Double = countOfPositiveExperiments.toDouble / count
  println(probability)    // При нескольких экспериментах примерно 0.3, что и следовало ожидать

}
