package isel.leic.tds.ttt.ui

import androidx.compose.runtime.*
import isel.leic.tds.storage.TextFileStorage
import isel.leic.tds.ttt.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TTTViewModel(val scope: CoroutineScope) {
    val storage = TextFileStorage<Name,Game>("games",GameSerializer)
    // Model State
    private var clash: Clash by mutableStateOf(Clash(storage))

    val hasClash: Boolean get() = clash is ClashRun
    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val score get() = (clash as ClashRun).game.score
    val sidePlayer get() = (clash as? ClashRun)?.sidePlayer
    val isSideTurn get() = clash.isSideTurn

    fun exit() {
        cancelWaiting()
        clash.exit()
    }

    fun play(pos: Position) {
        exec{ play(pos) }
        waitForOther()
    }
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
        cancelWaiting()
        exec{ when(action as Action) {
            Action.START -> start(name)
            Action.JOIN -> join(name)
        } }
        waitForOther()
        action = null
    }

    var message: String? by mutableStateOf(null)
        private set
    fun cancelMessage() { message = null }

    private fun exec( fx: Clash.()->Clash ) {
        try { clash = clash.fx() }
        catch(ex: Exception) {
            manageException(ex)
        }
    }

    private fun manageException(ex: Exception) {
        if (ex is IllegalArgumentException || ex is IllegalStateException) {
            message = ex.message
            if (ex is GameDeletedException)
                clash = Clash(storage)
        }
        else throw ex
    }

    private var waitingJob by mutableStateOf<Job?>(null)
    val isWaiting get() = waitingJob!=null

    private fun cancelWaiting() {
        waitingJob?.cancel()
        waitingJob = null
    }
    private fun waitForOther() {
        if (isSideTurn)  return
        waitingJob = scope.launch{
            while (true) {
                delay(5000)
                try {
                    clash = clash.refresh()
                    if (isSideTurn) break
                }
                catch (ex: NoModificationException) { /* ignore */ }
                catch (ex: Exception) { manageException(ex); break }
            }
            waitingJob = null
        }
    }
}