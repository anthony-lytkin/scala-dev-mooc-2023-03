package module1._homeworks.homework4

import module1._homeworks.homework4.HomeworksUtils.TaskSyntax

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.Future.successful
import scala.concurrent.impl.Promise
import scala.util.{Failure, Success, Try}

object TaskFutureSequence {

  /**
   * В данном задании Вам предлагается реализовать функцию fullSequence,
   * похожую на Future.sequence, но в отличии от нее,
   * возвращающую все успешные и не успешные результаты.
   * Возвращаемое тип функции - кортеж из двух списков,
   * в левом хранятся результаты успешных выполнений,
   * в правово результаты неуспешных выполнений.
   * Не допускается использование методов объекта Await и мутабельных переменных var
   */
  /**
   * @param futures список асинхронных задач
   * @return асинхронную задачу с кортежом из двух списков
   */
  def fullSequence[A](futures: List[Future[A]])
                     (implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {

    val process = futures.map { fut => fut.map(Success(_)) recover { case ex => Failure(ex) } }
    val result = Future.sequence(process)

    result.map { res => res.partitionMap(_.toEither.swap) }
  }
}
