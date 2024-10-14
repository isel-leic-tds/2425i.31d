package isel.leic.tds.ttt.model

@JvmInline
value class Position(val index: Int) {
    init {
        require(index in 0 ..< BOARD_SIZE) { "Invalid position $index"}
    }
    val row: Int get() = index / BOARD_DIM
    val col: Int get() = index % BOARD_DIM
    val backSlash get() = row == col
    val slash get() = row + col == BOARD_DIM-1
}

fun String.toPosition() = Position(
    requireNotNull(this.toIntOrNull()) { "Invalid position $this" }
)