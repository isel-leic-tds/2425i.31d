package isel.leic.tds.ttt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import isel.leic.tds.ttt.ui.Grid
import isel.leic.tds.ttt.ui.NameEdit
import isel.leic.tds.ttt.ui.ScoreDialog
import isel.leic.tds.ttt.ui.StatusBar
import isel.leic.tds.ttt.ui.TTTViewModel

@Composable
@Preview
private fun FrameWindowScope.TTTApp(onExit: () -> Unit) {
    val vm = remember{ TTTViewModel() }
    TTTMenu(vm, onExit= onExit)
    MaterialTheme {
        Column {
            Grid(vm.board, onClickCell = { pos -> vm.play(pos) })
            StatusBar(vm.board)
        }
        if (vm.scoreView)
            ScoreDialog(vm.score, onClose= vm::hideScore)
        vm.action?.let { NameEdit(it, vm::cancelAction, vm::doAction ) }
    }
}

@Composable
fun FrameWindowScope.TTTMenu(vm: TTTViewModel, onExit: ()->Unit) {
    MenuBar {
        Menu("Game") {
            Item("New board", enabled = vm.hasClash, onClick = vm::newBoard)
            Item("Score",  enabled = vm.hasClash, onClick = vm::showScore)
            Item("Exit", onClick = onExit )
            Item("Refresh",  enabled = vm.hasClash, onClick = vm::refresh)
        }
        Menu("Clash") {
            Item("Start", onClick = vm::start)
            Item("Join", onClick = vm::join)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize.Unspecified),
        title = "Tic Tac Toe"
    ) {
        TTTApp(onExit = ::exitApplication)
    }
}
