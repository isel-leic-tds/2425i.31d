package isel.leic.tds.ttt.model

@JvmInline
value class Position private constructor(val index: Int) {
    val row: Int get() = index / BOARD_DIM
    val col: Int get() = index % BOARD_DIM
    val backSlash get() = row == col
    val slash get() = row + col == BOARD_DIM-1
    companion object {
        val values = List(BOARD_SIZE) { Position(it) }
        operator fun invoke(idx: Int): Position {
            require(idx in 0 ..< BOARD_SIZE) { "Invalid position $idx"}
            return values[idx]
        }
    }
}

fun String.toPosition() = Position(
    requireNotNull(this.toIntOrNull()) { "Invalid position $this" }
)