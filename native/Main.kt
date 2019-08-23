import kotlinx.cinterop.*
import greeter.*

fun main(args: Array<String>) {
  println(hello(args.get(0))?.toKString())
}
