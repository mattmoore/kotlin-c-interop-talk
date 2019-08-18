import kotlinx.cinterop.*
import hello.*

fun main() {
  println(hello("Matt")?.toKString())
}
