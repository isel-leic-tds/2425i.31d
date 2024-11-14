import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.Grid

@Composable
@Preview
private fun GridApp() {
    var board: Board by remember { mutableStateOf(BoardRun(Player.X)) }
    MaterialTheme {
        Grid(board, onClickCell = { pos: Position ->
            try {
                board = board.play(pos)
            }catch (ex: Exception) {
                println(ex.message)
            }
        })
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize.Unspecified)
    ) {
        GridApp()
    }
}
