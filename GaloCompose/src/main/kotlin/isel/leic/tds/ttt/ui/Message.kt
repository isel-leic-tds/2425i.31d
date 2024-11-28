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
fun Message(
    message: String,
    onClose: ()->Unit,
) = AlertDialog(
    onDismissRequest = onClose,
    confirmButton = {
        TextButton(onClick = onClose) { Text("Ok") }
    },
    text = { Text(message, style = MaterialTheme.typography.body1) }
)
