
fun main(): Unit {
    val name = "Pedro"
    val age = readAge(default = 30)
    println("Hello, $name! You are $age years old.")
    hello("Alice")
    //println(hasInString("Hello, World!", 'o'))
    println("Hello, World!".hasIn('o'))
    println('o' in "Hello, World!")
}

fun hello(name: String= "World") = println("Hello, $name!")

fun readAge(name: String = "", default: Int = 18): Int {
    val msg = "Enter your age" +
            if (name.isEmpty()) "" else ", $name"
    print("$msg: ")
    val line = readln().trim()
    return if (line.isEmpty() || line[0] !in '0'..'9') default
    else line.toInt()
}

fun max(a: Int, b: Int) = if (a > b) a else b

fun String.hasIn(c: Char) = c in this