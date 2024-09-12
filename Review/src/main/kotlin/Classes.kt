fun main() {
    val obj = Thing()
    val obj2 = Thing()
    println(obj.toString())
    println(obj2.toString())
    println(obj == obj2)
    println(obj == obj)
}

class Thing: Any() {
    override fun equals(other: Any?) = true
    override fun toString() = "Thing"
    override fun hashCode() = 0
}