package isel.leic.tds.tennis.adhoc

private val ValidPoints = listOf(0, 15, 30, 40, 45)

/**
 * Represents the score of a tennis game.
 * A Draw is represented by Score(40,40) (Deuce).
 * Advantage is represented by Score(45,40) or Score(40,45).
 * End of the game (Game) will be represented by Score(45,?) or Score(?.45)
 * where ? is different from 40 to distinguish from the advantage representation.
 */
class Score(val pointsA: Int, val pointsB: Int) {
    init {
        require( pointsA in ValidPoints && pointsB in ValidPoints )
    }
}

val InitialScore = Score(0,0)

val Score.isGame get()= pointsA==45 && pointsB!=40 || pointsB==45 && pointsA!=40

private val Score.isDeuce get() = pointsA==40 && pointsB==40
private val Score.isAdvantage get() = pointsA==45 && pointsB==40 || pointsB==45 && pointsA==40

val Score.placard: String get() = when {
    isDeuce     -> "Deuce"
    isAdvantage -> "Advantage $playerOf45"
    isGame      -> "Game $playerOf45"
    else        -> "$pointsA - $pointsB"
}

private val Score.playerOf45 get() = when(45) {
    pointsA -> 'A'
    pointsB -> 'B'
    else -> error("Invalid points value")
}

// TODO: Validate the winner input to be 'A' or 'B' ???
fun Score.next(win: Char): Score = when {
    isAdvantage ->
        if (win == playerOf45)
            if (win == 'A') Score(45, 0) else Score(0, 45) // -> Game
        else Score(40, 40) // -> Deuce
    isGame ->
        error("Game over")
    else ->
        if (win == 'A') Score( pointsA.next(), pointsB )
        else Score( pointsA, pointsB.next())
}

private fun Int.next() = ValidPoints[ ValidPoints.indexOf(this)+1 ]

