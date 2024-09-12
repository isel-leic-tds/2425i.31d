
fun toLower(c: Char): Char = if (c in 'A'..'Z') c + ('a'-'A') else c
fun toUpper(c: Char): Char = if (c in 'a'..'z') c - ('a'-'A') else c


fun getFx(op: String): (Char) -> Char = when (op) {
    "UPPER" -> ::toUpper
    "LOWER" -> ::toLower
    else -> { c -> c }
}

fun printFx(msg: String, fx: (Char) -> Char) {
    msg.forEach { print(fx(it)) }
}

fun main() {
    //var fx: (Char)->Char = ::toUpper
    printFx("Hello, World!") { if (it in 'a'..'z') it - ('a'-'A') else it }
    //fx = ::toLower
    printFx("Hello, World!", ::toLower)
    printFx("Hello, World!", getFx("UPPER"))
}