
data class Thing(val p1: Int, val p2:String)

fun main() {
    val thing = Thing(10,"ISEL")
    println(thing)
    println(Thing(10,"ISEL")==thing)
    println(thing.hashCode())
    println(Thing(10,"ISEL").hashCode())
    println(Thing(11,"ISEL").hashCode())
}