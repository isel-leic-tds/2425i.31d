package isel.leic.tds.ttt.model

import isel.leic.tds.storage.Serializer

// example:
// X:0 Y:1 null:1
// X
// RUN O | 4:X 0:O 5:X
object GameSerializer: Serializer<Game> {
    override fun serialize(data: Game) = buildString {
        appendLine(data.score.entries.joinToString(" ") { (player,points) -> "$player:$points"})
        appendLine(data.firstPlayer)
        data.board?.let{ appendLine(BoardSerializer.serialize(it)) }
    }
    override fun deserialize(text: String): Game {
        val parts = text.split("\n")
        return Game(
            board = if (parts.size==2) null else BoardSerializer.deserialize(parts[2]),
            firstPlayer =  Player.valueOf(parts[1]),
            score = parts[0].split(" ")
                .map { it.split(":") }
                .associate { (player,points) ->
                    Player.entries.firstOrNull { it.name==player } to points.toInt()
                }
        )
    }
}

// example: "RUN O | 4:X 0:O 5:X"
object BoardSerializer: Serializer<Board> {
    override fun serialize(data: Board): String =
        when(data) {
            is BoardRun -> "RUN ${data.turn}"
            is BoardWin -> "WIN ${data.winner}"
            is BoardDraw -> "DRAW " // final space to simplify deserializing
        } + " | " +
        data.moves.entries.joinToString(" ") { (pos,player) -> "$pos:$player" }

    override fun deserialize(text: String): Board {
        val (left,right) = text.split(" | ")
        val moves = if (right.isEmpty()) mapOf() else right
            .split(" ").map { it.split(":") }
            .associate { (pos,player) ->
                pos.toPosition() to Player.valueOf(player)
            }
        val (type,player) = left.split(" ")
        return when(type) {
            "RUN" -> BoardRun(Player.valueOf(player), moves)
            "WIN" -> BoardWin(moves,Player.valueOf(player))
            "DRAW" -> BoardDraw(moves)
            else -> error("Illegal board type $type")
        }
    }
}