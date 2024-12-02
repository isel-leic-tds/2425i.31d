package isel.leic.tds.ttt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import isel.leic.tds.ttt.ui.*

@Composable
@Preview
private fun FrameWindowScope.TTTApp(vm: TTTViewModel) {
    MaterialTheme {
        Column {
            Grid(vm.board, onClickCell = { pos -> vm.play(pos) })
            StatusBar(vm.board, vm.sidePlayer)
        }
        if (vm.scoreView) ScoreDialog(vm.score, onClose= vm::hideScore)
        vm.action?.let { NameEdit(it, vm::cancelAction, vm::doAction ) }
        vm.message?.let { Message(it, vm::cancelMessage) }
        if (vm.isWaiting) Waiting()
    }
}

@Composable
fun FrameWindowScope.TTTMenu(vm: TTTViewModel, onExit: ()->Unit) {
    MenuBar {
        Menu("Game") {
            Item("New board", enabled = vm.isSideTurn, onClick = vm::newBoard)
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
    val scope = rememberCoroutineScope()
    val vm = remember{ TTTViewModel(scope) }
    val onExit = { vm.exit(); exitApplication() }
    Window(
        onCloseRequest = onExit,
        state = WindowState(size = DpSize.Unspecified),
        title = "Tic Tac Toe"
    ) {
        TTTMenu(vm, onExit)
        TTTApp(vm)
    }
}
