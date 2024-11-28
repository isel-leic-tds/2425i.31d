import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun log(label: String) =
    println("$label: ${Thread.currentThread().name}")

fun main() {
    log("Start main")
    application(exitProcessOnExit = false) {
        log("Start application")
        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(size = DpSize(300.dp, 100.dp)),
        ) {
            val scope = rememberCoroutineScope()
            //var clickable by remember { mutableStateOf(true) }
            var job by remember { mutableStateOf<Job?>(null) }
            val clickable = job == null
            Row {
                Button( enabled = clickable,
                    onClick = {
                        log("onClick")
                        job = scope.launch {
                            log("thread")
                            repeat(5) { delay(1000); print("$it ") }
                            println()
                            job = null
                        }
                    }
                ) { Text("Click me") }
                Button( enabled = !clickable,
                    onClick = {
                        job?.cancel()
                        job = null
                    }
                ) { Text("Enable click") }
            }
        }
        log("End application")
    }
    log("End main")
}