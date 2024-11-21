package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import isel.leic.tds.ttt.model.Player
import isel.leic.tds.ttt.model.Score


@Composable
@Preview
// It is not yet possible to preview Dialogs on the desktop
// because they assume that there is a base Window.
fun ScoreDialogPreview()  {
        ScoreDialog(
            score = (Player.entries+null).associateWith { 0 },
            onClose = { }
        )
    }

@Composable
fun ScoreDialog(
    score: Score,
    onClose: ()->Unit,
) = AlertDialog(
    onDismissRequest = onClose,
    confirmButton = {
        TextButton(onClick = onClose) { Text("Close") }
    },
    title = { Text("Score", style = MaterialTheme.typography.h3) },
    text = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Player.entries.forEach {
                    Row{
                        Player(it, modifier = Modifier.size(32.dp))
                        Text(" - ${score[it]}", style = MaterialTheme.typography.h4)
                    }
                }
            }
            Text("Draws - ${score[null]}", style = MaterialTheme.typography.h5)
        }
    }
)

