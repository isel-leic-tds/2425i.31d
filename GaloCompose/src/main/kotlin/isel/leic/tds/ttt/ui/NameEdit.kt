package isel.leic.tds.ttt.ui

import androidx.compose.runtime.Composable

enum class Action(val text: String) {
    START("Start"),
    JOIN("Join")
}

@Composable
fun NameEdit(
    action: Action,
    onCancel: ()->Unit,
    onAction: (String)->Unit
) {

}