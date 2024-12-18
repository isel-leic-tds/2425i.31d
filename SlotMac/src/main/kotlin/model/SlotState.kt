package model

/*
Defina o tipo imutável SlotState (o modelo) para guardar o estado das 3 imagens a apresentar.
Cada imagemé representada por um byte com valores de 0 a 9 (10 imagens diferentes).
A criação de instâncias de SlotStateé realizada através da chamada SlotState.random(),
que deve retornar uma instância com 3 valores/imagens
aleatórias.
A função extensão isWinner deve retornar true caso todos os estados sejam iguais.
 */

const val SLOTS = 3
val RANGE_VALUES = 0..9
class SlotState(val slots: List<Byte>) {
    companion object{
        fun random() = SlotState(List(SLOTS){ RANGE_VALUES.random().toByte() })
    }
}

fun SlotState.isWinner() = slots.drop(1).all{ it == slots.first() }