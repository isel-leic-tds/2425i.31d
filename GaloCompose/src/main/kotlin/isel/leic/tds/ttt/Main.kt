package isel.leic.tds.ttt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*

import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.Grid
import isel.leic.tds.ttt.ui.StatusBar

@Composable
@Preview
private fun TTTApp() {
    var board: Board by remember { mutableStateOf(BoardRun(Player.X)) }
    MaterialTheme {
        Column {
            Grid(board, onClickCell = { pos: Position ->
                if (board is BoardRun) board = board.play(pos)
            })
            StatusBar(board)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize.Unspecified),
        title = "Tic Tac Toe"
    ) {
        TTTApp()
    }
}
