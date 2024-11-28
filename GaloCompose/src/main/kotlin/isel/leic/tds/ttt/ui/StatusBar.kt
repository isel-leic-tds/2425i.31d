package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

import isel.leic.tds.ttt.model.*

@Composable
fun StatusBar(board: Board?, you: Player?) {
    Row(
        modifier = Modifier.width(GRID_WIDTH).background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        you?.let {
            Text("You: ", fontSize = 32.sp)
            Player(it,modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(32.dp))
        }
        val (state,player) = when(board) {
            is BoardRun -> "Turn: " to board.turn
            is BoardWin -> "Winner: " to board.winner
            is BoardDraw -> "Draw" to null
            null -> "No board" to null
        }
        Text(state, fontSize = 32.sp)
        player?.let { Player(it, modifier = Modifier.size(32.dp)) }
    }
}

@Composable
@Preview
fun StatusBarPreview() {
    StatusBar(BoardRun(Player.X), Player.O)
}