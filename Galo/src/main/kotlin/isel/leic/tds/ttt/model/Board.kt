package isel.leic.tds.ttt.model

const val BOARD_DIM = 3
const val BOARD_SIZE = BOARD_DIM * BOARD_DIM

class Board(
    val turn: Player,
    val moves: List<Player?> = List(BOARD_SIZE) { null }
)

fun Board.play(pos: Position): Board {
    check(moves[pos.index] == null) { "Position already used" }
    return Board(
        turn = turn.other,
        moves.mapIndexed { idx, m -> if (idx==pos.index) turn else m }
    )
}

fun Board.isWinner(p: Player): Boolean =
    // Columns
    (0..<BOARD_DIM).any { col ->
        (0..<BOARD_SIZE step BOARD_DIM).all { moves[col+it] == p }
    } ||
    // Rows
    (0..<BOARD_SIZE step BOARD_DIM).any { row ->
        (0..<BOARD_DIM).all { moves[row+it] == p }
    } ||
    // Diagonal
    (0..BOARD_SIZE step BOARD_DIM+1).all { moves[it] == p } ||
    // Reverse diagonal
    ((BOARD_DIM-1)..<BOARD_SIZE-1 step BOARD_DIM-1).all { moves[it] == p }

fun Board.isDraw() = moves.all { it != null }