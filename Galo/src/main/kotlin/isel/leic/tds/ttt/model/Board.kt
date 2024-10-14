package isel.leic.tds.ttt.model

const val BOARD_DIM = 3
const val BOARD_SIZE = BOARD_DIM * BOARD_DIM

class Board(
    val turn: Char,
    val moves: List<Char> = List(BOARD_SIZE) { ' ' }
)

fun Board.play(pos: Int): Board {
    check(pos in 0 ..< BOARD_SIZE) { "Invalid position $pos" }
    check(moves[pos] == ' ') { "Position already used" }
    return Board(
        turn = if (turn=='X') 'O' else 'X',
        moves.mapIndexed { idx, m -> if (idx==pos) turn else m }
    )
}

fun Board.isWinner(p: Char): Boolean =
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

fun Board.isDraw() = moves.all { it != ' ' }