package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*

import isel.leic.tds.ttt.model.Player

@Composable
fun Player(player: Player?, onClick: ()->Unit = {} ,modifier: Modifier = Modifier.size(100.dp)) {
    if (player==null)
        Box(modifier.clickable(onClick = onClick))
    else {
        val file = when (player) {
            Player.X -> "cross"
            Player.O -> "circle"
        }
        Image(
            painter = painterResource("$file.png"),
            contentDescription = "Player $file",
            modifier = modifier
        )
    }
}

@Composable
@Preview
fun PlayerXPreview() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Player(Player.X, modifier = Modifier.background(Color.Yellow).size(100.dp))
        Player(null)
        Player(Player.O)
    }
}
