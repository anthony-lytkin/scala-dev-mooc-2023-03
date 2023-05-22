package _homeworks.homework6.parser

class MonadParser[T, Src](private val p: Src => (T, Src)) {

  def flatMap[M](f: T => MonadParser[M, Src]): MonadParser[M, Src] = MonadParser { src =>
    val (word, rest) = p(src)
    val mn = f(word)
    val res = mn.p(rest)

    //с помощью функции — аргумента метода добавляем его в контекст, видимый всем последующим парсерам по цепочке.
    res
  }

  def map[M](f: T => M): MonadParser[M, Src] = MonadParser { src =>
    val (word, rest) = p(src)
    (f(word), rest)
  }

  def parse(src: Src): T = p(src)._1

}

object MonadParser {

  def apply[T, Src](f: Src => (T, Src)) = new MonadParser[T, Src](f)

  def stringMonadParser: MonadParser[String, String] = apply { str =>
    val idx = str.indexOf(";")
    if (idx > -1)
      (str.substring(0, idx), str.substring(idx + 1))
    else
      (str, "")
  }


}
