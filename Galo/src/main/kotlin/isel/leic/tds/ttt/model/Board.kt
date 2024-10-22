package isel.leic.tds.ttt.model

const val BOARD_DIM = 3
const val BOARD_SIZE = BOARD_DIM * BOARD_DIM

// Sealed hierarchy for board state (Run, Win and Draw)
typealias Moves = Map<Position,Player>
/*
sealed class Board(val moves: Moves)
class BoardRun(moves: Moves, val turn: Player) : Board(moves)
class BoardWin(moves: Moves, val winner: Player) : Board(moves)
class BoardDraw(moves: Moves) : Board(moves)
*/

class Board(
    val turn: Player,
    val moves: List<Player?> = List(BOARD_SIZE) { null },
    val winner: Player? = null
)

operator fun Board.get(p: Position): Player? = moves[p.index]

fun Board.play(pos: Position): Board {
    check(moves[pos.index] == null) { "Position already used" }
    check(winner==null) { "Game is over" }
    val movesAfterPlay = moves.mapIndexed { idx, m -> if (idx==pos.index) turn else m }
    return Board(
        turn = turn.other,
        moves = movesAfterPlay,
        winner = winnerIn(pos, movesAfterPlay)
    )
}

private fun winnerIn(pos: Position, moves: List<Player?>): Player? {
    val player = checkNotNull(moves[pos.index])
/*  if (moves.count { it == player } < BOARD_DIM) return null
    return player.takeIf {
        (0..<BOARD_SIZE step BOARD_DIM).all { moves[pos.col+it]==player } ||
        (0..<BOARD_DIM).all { moves[pos.row* BOARD_DIM+it] == player } ||
        pos.slash && ((BOARD_DIM-1)..<BOARD_SIZE-1 step BOARD_DIM-1).all { moves[it] == player } ||
        pos.backSlash && (0..BOARD_SIZE step BOARD_DIM+1).all { moves[it] == player }
    } */
    val places = Position.values.filter { moves[it.index] == player }
    if (places.size < BOARD_DIM) return null
    return player.takeIf {
        places.count { it.col == pos.col } == BOARD_DIM ||
        places.count { it.row == pos.row } == BOARD_DIM ||
        pos.slash && places.count { it.slash } == BOARD_DIM ||
        pos.backSlash && places.count { it.backSlash } == BOARD_DIM
    }
}

fun Board.isDraw() = winner==null && moves.all { it != null }
