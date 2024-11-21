package isel.leic.tds.ttt.ui

import androidx.compose.runtime.*
import isel.leic.tds.ttt.model.*

class TTTViewModel {
    // Model State
    private var game: Game by mutableStateOf(Game())

    val board: Board? get() = game.board
    val score get() = game.score

    fun play(pos: Position) {
        if (board is BoardRun) game = game.play(pos)
    }
    fun newBoard() { game = game.new() }

    // UI State
    var scoreView by mutableStateOf(false)
    private set

    fun showScore() { scoreView = true }
    fun hideScore() { scoreView = false }
}