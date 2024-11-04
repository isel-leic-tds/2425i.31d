package isel.leic.tds.ttt.ui

import isel.leic.tds.ttt.model.*

fun Clash.showScore() = (this as? ClashRun)?.game?.showScore()

fun Clash.show() = (this as? ClashRun)?.game?.show()

fun Game.showScore() {
    (Player.entries+null).forEach {
        println("${it?:"Draw"} = ${score[it]}")
    }
}

fun Game.show() = board?.show()

val sepLine = "---+".repeat(BOARD_DIM).dropLast(1)

fun Board.show() {
    Position.values.forEach { pos ->
        print(" ${this[pos] ?:' '} ")
        if (pos.col < BOARD_DIM-1) print('|')
        else {
            println()
            if (pos.row < BOARD_DIM-1) println(sepLine)
        }
    }
    println( when(this) {
        is BoardWin -> "Winner: $winner"
        is BoardDraw -> "Draw"
        is BoardRun -> "turn: $turn"
    } )
}