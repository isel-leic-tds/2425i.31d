package viewmodel

import androidx.compose.runtime.*
import model.SlotState
import model.isWinner

/*
Implemente a classe SlotMachineViewModel com as propriedades mutáveis playerName e slotState.
Esta classe tem as funções play e isWinner, que devem chamar as funções do modelo,
e a função isPlayerNameValid que verifica se um nome de jogador tem pelo menos
3 caracteres que não são separadores.
 */
class SlotMachineViewModel {
    var playerName by mutableStateOf("")
    var slotState by mutableStateOf(SlotState.random())
        private set

    fun play() { slotState = SlotState.random() }
    fun isWinner() = slotState.isWinner()
    fun isPlayerNameValid() = playerName.count { !it.isWhitespace() } >= 3
}