package _homeworks.homework7

object Magnet {

  enum CompletionArg:
    case StringArg(s: String)
    case IntArg(i: Int)
    case FloatArg(f: Float)

  object CompletionArg {
    given fromString: Conversion[String, CompletionArg] = StringArg(_)
    given fromInt: Conversion[Int, CompletionArg] = IntArg(_)
    given fromFloat: Conversion[Float, CompletionArg] = FloatArg(_)
  }

  import CompletionArg.*

  def complete(arg: CompletionArg) = arg match
    case StringArg(s) => s"Completed StringArg: $s."
    case IntArg(i)    => s"Completed IntArg: $i"
    case FloatArg(f)  => s"Completed FloatArg: $f"

  @main def mainMagnet() = {
    println(complete("String"))
    println(complete(1))
    println(complete(7f))
  }


}
