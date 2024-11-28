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
    val sidePlayer get() = (clash as? ClashRun)?.sidePlayer

    fun exit() { clash.exit() }
    fun play(pos: Position) = exec{ play(pos) }
    fun newBoard() = exec(Clash::newBoard)

    // UI State
    var scoreView by mutableStateOf(false)
    private set

    fun showScore() { scoreView = true }
    fun hideScore() { scoreView = false }

    var action: Action? by mutableStateOf(null)
        private set
    fun refresh() = exec( Clash::refresh )
    fun start() { action = Action.START }
    fun join() { action = Action.JOIN }
    fun cancelAction() { action = null }
    fun doAction(name: Name) {
        exec{ when(action as Action) {
            Action.START -> start(name)
            Action.JOIN -> join(name)
        } }
        action = null
    }

    var message: String? by mutableStateOf(null)
        private set
    fun cancelMessage() { message = null }

    private fun exec( fx: Clash.()->Clash ) {
        try { clash = clash.fx() }
        catch(ex: Exception) {
            if (ex is IllegalArgumentException ||
                ex is IllegalStateException
            ) message = ex.message
            else throw ex
        }
    }
}