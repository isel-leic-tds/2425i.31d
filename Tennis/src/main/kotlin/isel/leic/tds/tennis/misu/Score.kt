package isel.leic.tds.tennis.misu

import isel.leic.tds.tennis.*
import isel.leic.tds.tennis.Points.*

class Score(val pointsA: Points, val pointsB: Points)

private val Score.playerOf45 get() = when(GAME) {
    pointsA -> Player.A
    pointsB -> Player.B
    else -> error("Invalid points value")
}

val Score.isGame get() =
    pointsA==GAME && pointsB!=FORTY || pointsB==GAME && pointsA!=FORTY
private val Score.isAdvantage get() =
    pointsA==GAME && pointsB==FORTY || pointsB==GAME && pointsA==FORTY

val Score.placard get() = when {
    pointsA==FORTY && pointsB==FORTY -> "Deuce"
    isAdvantage -> "Advantage $playerOf45"
    isGame -> "Game $playerOf45"
    else -> "${pointsA.value} - ${pointsB.value}"
}

fun Score.next(win: Player): Score = when {
    isAdvantage ->
        if (win == playerOf45)
            if (win == Player.A) Score(GAME,LOVE) else Score(LOVE,GAME)
        else Score(FORTY,FORTY)
    isGame -> error("Game over")
    else ->
        if (win == Player.A) Score( pointsA.next(), pointsB )
        else Score( pointsA, pointsB.next())
}

val InitialScore = Score(LOVE,LOVE)

