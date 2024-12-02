package isel.leic.tds.ttt.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun Waiting() {
    CircularProgressIndicator(
        Modifier.size(GRID_WIDTH).padding(32.dp),
        strokeWidth = 16.dp,
        strokeCap = StrokeCap.Round
    )
}