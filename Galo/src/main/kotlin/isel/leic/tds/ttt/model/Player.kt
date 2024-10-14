package isel.leic.tds.ttt.model

enum class Player {
    X, O;
    val other get() = if (this==X) O else X
}