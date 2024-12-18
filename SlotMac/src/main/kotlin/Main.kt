import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import model.SlotState
import view.toSlotImageFilename
import viewmodel.SlotMachineViewModel

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Slot Machine")
    { MaterialTheme { SlotMachineApp() } }
}
@Composable
fun SlotMachineApp() {
    val slotMachineViewModel = remember { SlotMachineViewModel() }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TODO: Implemente o código em falta da função SlotMachineApp para compor a aplicação
        //utilizando as funções composable indicadas no trecho de código
        PlayerInputDetails(
            { slotMachineViewModel.playerName },
            { slotMachineViewModel.playerName = it }
        )
        SlotMachine(
            { slotMachineViewModel.slotState },
            slotMachineViewModel::isPlayerNameValid,
            slotMachineViewModel::play
        )
        ResultPanel(slotMachineViewModel)
    }
}
@Composable
fun PlayerInputDetails(nameGetter: () -> String, nameSetter: (String) -> Unit) {
    //TODO:
    TextField(nameGetter(), nameSetter)
}
@Composable
fun SlotMachine(slotState: () -> SlotState, isEnabled: () -> Boolean, play: () -> Unit) {
    //TODO:
    Row {
        slotState().slots.forEach {
            Image(painterResource(it.toSlotImageFilename()), contentDescription = null,
                modifier = Modifier.size(100.dp))
        }
    }
    Button(play, enabled = isEnabled()) { Text("Play") }
}
@Composable
fun ResultPanel(slotMachineViewModel: SlotMachineViewModel) {
    //TODO:
    val txt = when {
        !slotMachineViewModel.isPlayerNameValid() -> "Insert your name"
        slotMachineViewModel.isWinner() -> "Congratulations ${slotMachineViewModel.playerName}"
        else -> "Better luck next time ${slotMachineViewModel.playerName}"
    }
    Text(txt)
}
