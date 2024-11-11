package isel.leic.tds.ttt.model

const val BOARD_DIM = 3
const val BOARD_SIZE = BOARD_DIM * BOARD_DIM

// Sealed hierarchy for board state (Run, Win and Draw)
typealias Moves = Map<Position,Player>

sealed class Board(val moves: Moves) {
    override fun equals(other: Any?): Boolean =
        other is Board && moves == other.moves
    override fun hashCode() = moves.hashCode()
}
class BoardRun(val turn: Player, moves: Moves = emptyMap()) : Board(moves)
class BoardWin(moves: Moves, val winner: Player) : Board(moves)
class BoardDraw(moves: Moves) : Board(moves)

operator fun Board.get(p: Position): Player? = moves[p]

fun Board.play(pos: Position): Board {
    check(moves[pos] == null) { "Position already used" }
    when (this) {
        is BoardRun -> {
            val movesAfter = moves + (pos to turn)
            val winner = winnerIn(pos, movesAfter)
            return when {
                winner != null -> BoardWin(movesAfter, winner)
                movesAfter.size == BOARD_SIZE -> BoardDraw(movesAfter)
                else -> BoardRun(turn.other, movesAfter)
            }
        }
        is BoardWin,
        is BoardDraw -> error("Game is over")
    }
}

private fun winnerIn(pos: Position, moves: Moves): Player? {
    val player = checkNotNull(moves[pos])
    val places = Position.values.filter { moves[it] == player }
    if (places.size < BOARD_DIM) return null
    return player.takeIf {
        places.count { it.col == pos.col } == BOARD_DIM ||
        places.count { it.row == pos.row } == BOARD_DIM ||
        pos.slash && places.count { it.slash } == BOARD_DIM ||
        pos.backSlash && places.count { it.backSlash } == BOARD_DIM
    }
}

//fun Board.isDraw() = winner==null && moves.all { it != null }
