package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import isel.leic.tds.ttt.model.*

val CELL_SIZE = 100.dp
val LINE_WIDTH = 5.dp
val GRID_WIDTH = CELL_SIZE * BOARD_DIM + LINE_WIDTH * (BOARD_DIM-1)

@Composable
fun Grid(board: Board, onClickCell: (Position)->Unit ) {
    Column(
        modifier = Modifier.size(GRID_WIDTH).background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(BOARD_DIM) { lin ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(BOARD_DIM) { col ->
                    val pos = Position(lin * BOARD_DIM+col)
                    Player(
                        player = board[pos],
                        onClick= { onClickCell(pos) },
                        modifier = Modifier.size(CELL_SIZE).background(Color.White)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun GridPreview() {
    val board = BoardRun(Player.X)
        .play(Position(4))
        .play(Position(0))
    Grid(board, {})
}