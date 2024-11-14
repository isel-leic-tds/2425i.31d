import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import isel.leic.tds.ttt.model.Player
import isel.leic.tds.ttt.ui.Player

@Composable
@Preview
private fun PlayerApp() {
    var player by remember { mutableStateOf(Player.X) }
    MaterialTheme {
        Column(
            modifier = Modifier.background(Color.Yellow).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Player(player)
            Button(onClick = {
                player = player.other
            }) {
                Text("Change Player")
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize(180.dp,180.dp))
    ) {
        PlayerApp()
    }
}
