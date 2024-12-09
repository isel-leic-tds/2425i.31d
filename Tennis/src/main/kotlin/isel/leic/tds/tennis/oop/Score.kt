package isel.leic.tds.tennis.oop

import isel.leic.tds.tennis.*
import isel.leic.tds.tennis.Points.*

/**
 * State Machine for Tennis Score.
 * Interface of each state (OO State Machine Pattern).
 */
interface Score {
    val placard: String
    val isGame get() = false
    fun next(win: Player): Score  // Transition function of the state machine
}

private class Game(val winner: Player): Score {
    override val placard get() = "Game $winner"
    override val isGame get() = true
    override fun next(win: Player) = error("Game over")
}

private object Deuce: Score {
    override val placard get() = "Deuce"
    override fun next(win: Player) = Advantage(win)
}

private class Advantage(val player: Player): Score {
    override val placard get() = "Advantage $player"
    override fun next(win: Player) = if (win==player) Game(win) else Deuce
}

private class Forty(val player: Player, val pointsOther: Points): Score {
    override val placard get() =
        if (player == Player.A) "40 - ${pointsOther.value}"
        else "${pointsOther.value} - 40"
    override fun next(win: Player) = when {
        win == player -> Game(win)
        pointsOther == THIRTY -> Deuce
        else -> Forty(player, pointsOther.next())
    }
}

private class ByPoints(val pointsA: Points, val pointsB: Points): Score {
    private fun pointsOf(p: Player) = if (p==Player.A) pointsA else pointsB
    override val placard get() = "${pointsA.value} - ${pointsB.value}"
    override fun next(win: Player) = when {
        pointsOf(win) == THIRTY -> Forty(win, pointsOf(win.other()))
        win == Player.A -> ByPoints( pointsA.next(), pointsB )
        else -> ByPoints( pointsA, pointsB.next())
    }
/*
    override fun next(win: Player) = when {
        pointsOf(win) == FORTY -> Game(win)
        pointsOf(win) == THIRTY && pointsOf(win.other()) == FORTY -> Deuce
        win == Player.A -> ByPoints( pointsA.next(), pointsB )
        else -> ByPoints( pointsA, pointsB.next() )
    }
*/
}

val InitialScore: Score = ByPoints(LOVE,LOVE)


