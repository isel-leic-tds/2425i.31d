package isel.leic.tds.ttt.model

typealias Score = Map<Player?,Int>

data class Game(
    val board: Board? = null,
    val score: Score = (Player.entries+null).associateWith { 0 },
    val firstPlayer: Player = Player.entries.first()
)

private fun Game.advanceScore(player: Player?): Score =
    score - player + (player to checkNotNull(score[player])+1)

fun Game.new(): Game = Game (
    board = BoardRun(turn= firstPlayer),
    score = if (board is BoardRun) advanceScore(board.turn.other) else score,
    firstPlayer = firstPlayer.other
)

fun Game.play(pos: Position): Game {
    checkNotNull(board) { "No board" }
    val board = board.play(pos)
    return copy(
        board = board,
        score = when(board) {
            is BoardWin -> advanceScore(board.winner)
            is BoardDraw -> advanceScore(null)
            is BoardRun -> score
        }
    )
}