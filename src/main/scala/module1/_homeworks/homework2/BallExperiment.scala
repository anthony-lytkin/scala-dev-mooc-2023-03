package module1._homeworks.homework2

import module1._homeworks.homework2.Ball._

import scala.util.Random

class BallExperiment(countWhiteBalls: Int, countBlackBalls: Int) {

  /**
   * Начальная инициализация коробочки необходимым количеством шариков
   */
  private def initBallBox: List[Ball] = {
    val whiteBalls = List.fill(countWhiteBalls)(WHITE_BALL)
    val blackBalls = List.fill(countBlackBalls)(BLACK_BALL)
    whiteBalls ::: blackBalls
  }

  val ballsBox: List[Ball] = Random.shuffle(initBallBox)    // Создали и перемешали коробочку с шариками

  def isFirstBlackSecondWhite: Boolean = {
    val firstBall = ballsBox.head                           // Взяли первый шарик
    val secondBall = Random.shuffle(ballsBox.tail).head     // Перемешали остаток и взяли снова первый шарик из остатка
    firstBall.isBlack && secondBall.isWhite                 // Проверили успешность эксперимента
  }

}
