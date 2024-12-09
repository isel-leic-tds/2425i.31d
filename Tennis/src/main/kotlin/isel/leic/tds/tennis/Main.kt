package isel.leic.tds.tennis
//import isel.leic.tds.tennis.adhoc.*
//import isel.leic.tds.tennis.misu.*
//import isel.leic.tds.tennis.oop.*
//import isel.leic.tds.tennis.pm.*
import isel.leic.tds.tennis.fp.*

fun main() {
    var score: Score = InitialScore  // Mutable state
    while (true) {
        println(score.placard)
        if( score.isGame ) break
        score = score.next( readWinner() )
    }
}

/*tailrec fun readWinner(): Char {
    print("Winner A or B? ")
    return readln().trim().uppercase().firstOrNull()?.takeIf{ it in "AB" }
        ?: readWinner()
}*/
