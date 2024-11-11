import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
private fun App() {
    print("App ")
    val text = mutableStateOf("Hello, World!")
    Button(onClick = {
        print("\nClick: ")
        text.value = "Hello, Desktop!"
    }) {
        print("Text = ${text.value} ")
        Text(text.value)
    }
    print("App end. ")
}

fun main() {
    print("Main ")
    application {
        print("Application ")
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
        print("Application end. ")
    }
    print("Main end. ")  // This is printed?
}
