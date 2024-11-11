package tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import isel.leic.tds.ttt.model.Player

@Composable
fun PlayerView(size: Dp, player: Player) {
    val file = when (player) {
        Player.X -> "cross"
        Player.O -> "circle"
    }
    Image(
        painter = painterResource("$file.png"),
        contentDescription = "Player $file",
        modifier = Modifier.size(size).background(Color.White)
    )
}

@Composable
@Preview
fun PlayerXPreview() {
    PlayerView(100.dp, Player.X)
    PlayerView(50.dp, Player.O)
}
