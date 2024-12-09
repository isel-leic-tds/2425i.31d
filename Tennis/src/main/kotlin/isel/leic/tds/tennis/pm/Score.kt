package isel.leic.tds.tennis.pm

import isel.leic.tds.tennis.*
import isel.leic.tds.tennis.Points.*

sealed interface Score
private class ByPoints(val pointsA: Points, val pointsB: Points): Score
private class Forty(val player: Player, val pointsOther: Points): Score
private class Game(val winner: Player): Score
private object Deuce: Score
private class Advantage(val player: Player): Score

val Score.isGame get() = this is Game

val InitialScore: Score = ByPoints(LOVE,LOVE)

val Score.placard get() = when (this) {
    is ByPoints -> "${pointsA.value} - ${pointsB.value}"
    is Forty -> if (player==Player.A) "40 - ${pointsOther.value}"
                else "${pointsOther.value} - 40"
    is Game -> "Game $winner"
    is Deuce -> "Deuce"
    is Advantage -> "Advantage $player"
}

private fun ByPoints.pointsOf(p: Player) = if (p==Player.A) pointsA else pointsB

fun Score.next(win: Player): Score = when (this) {
    is ByPoints -> when {
        pointsOf(win) == THIRTY -> Forty(win, pointsOf(win.other()))
        win == Player.A -> ByPoints( pointsA.next(), pointsB )
        else -> ByPoints( pointsA, pointsB.next())
    }
    is Forty -> when {
        win == player -> Game(win)
        pointsOther == THIRTY -> Deuce
        else -> Forty(player, pointsOther.next())
    }
    is Game -> error("Game over")
    Deuce -> Advantage(win)
    is Advantage -> if (win==player) Game(win) else Deuce
}

