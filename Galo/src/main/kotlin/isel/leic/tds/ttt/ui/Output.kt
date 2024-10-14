package isel.leic.tds.ttt.ui

import isel.leic.tds.ttt.model.*

val sepLine = "---+".repeat(BOARD_DIM).dropLast(1)

fun Board.show() {
    repeat(BOARD_DIM) { line ->
        val startPos = line* BOARD_DIM
        for(pos in startPos ..< startPos+ BOARD_DIM) {
            print(" ${moves[pos]?:' '} ")
            if (pos == startPos+ BOARD_DIM -1) println()
            else print("|")
        }
        if (line < BOARD_DIM -1) println(sepLine)
    }
    when {
        isWinner(Player.X) -> println("Winner: X")
        isWinner(Player.O) -> println("Winner: O")
        isDraw() -> println("Draw")
        else -> println("turn: $turn")
    }
}