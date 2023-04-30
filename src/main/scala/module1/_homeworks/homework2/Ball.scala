package module1._homeworks.homework2

import module1._homeworks.homework2.Ball.WHITE_BALL

import scala.util.Random

final class Ball private (val color: Int) extends AnyVal {

  def isWhite: Boolean = this == WHITE_BALL
  def isBlack: Boolean = !isWhite

  override def toString: String = color.toString
}

object Ball {

  val WHITE_BALL = new Ball(1)
  val BLACK_BALL = new Ball(0)

  def randomBall: Ball = {
    val rnd = Random.between(0, 2) // rnd = 1 means random white ball, rnd = 0 - means black ball
    rnd match {
      case 0 => BLACK_BALL
      case 1 => WHITE_BALL
      case _ => throw new MatchError("Something went wrong with your random ball")
    }
  }

}
