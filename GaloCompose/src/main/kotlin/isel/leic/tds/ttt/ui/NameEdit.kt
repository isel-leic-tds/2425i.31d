package isel.leic.tds.ttt.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import isel.leic.tds.ttt.model.Name

// TODO: Must be moved to ViewModel
enum class Action(val text: String) {
    START("Start"),
    JOIN("Join")
}

@Composable
fun NameEdit(
    action: Action,
    onCancel: ()->Unit,
    onAction: (Name)->Unit
) {
    var txt by remember{ mutableStateOf("") }
    AlertDialog(
       title = { Text("Name to ${action.text}", style = MaterialTheme.typography.h4) },
       onDismissRequest = { },
       confirmButton = {
           TextButton(
               enabled = Name.isValid(txt),
               onClick = { onAction(Name(txt)) }
           ) { Text(action.text) }
       },
       dismissButton = {
           TextButton(onClick = onCancel) { Text("Cancel") }
       },
       text = {
           OutlinedTextField(txt, onValueChange = { txt = it }, label = { Text("Name")})
       }
    )
}