package isel.leic.tds.ttt.ui

import androidx.compose.runtime.*
import isel.leic.tds.storage.TextFileStorage
import isel.leic.tds.ttt.model.*

class TTTViewModel {
    val storage = TextFileStorage<Name,Game>("games",GameSerializer)
    // Model State
    private var clash: Clash by mutableStateOf(Clash(storage))

    val hasClash: Boolean get() = clash is ClashRun
    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val score get() = (clash as ClashRun).game.score

    fun play(pos: Position) {
        if (board is BoardRun) clash = clash.play(pos)
    }
    fun newBoard() { clash = clash.newBoard() }

    // UI State
    var scoreView by mutableStateOf(false)
    private set

    fun showScore() { scoreView = true }
    fun hideScore() { scoreView = false }

    var action: Action? by mutableStateOf(null)
        private set
    fun refresh() { clash = clash.refresh() }
    fun start() { action = Action.START }
    fun join() { action = Action.JOIN }
    fun cancelAction() { action = null }
    fun doAction(name: Name) {
        clash = when(action as Action) {
            Action.START -> clash.start(name)
            Action.JOIN -> clash.join(name)
        }
        action = null
    }
}