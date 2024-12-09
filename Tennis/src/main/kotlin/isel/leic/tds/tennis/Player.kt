package isel.leic.tds.tennis

enum class Player{ A, B;
    fun other() = if (this==A) B else A
}

fun String.toPlayerOrNull() = Player.entries.firstOrNull{ it.name == this }
fun String.toPlayer() = requireNotNull( toPlayerOrNull() )

tailrec fun readWinner(): Player {
    print("Winner A or B? ")
    return readln().trim().uppercase().toPlayerOrNull() ?: readWinner()
}
