package isel.leic.tds.ttt.model

import isel.leic.tds.storage.Storage

@JvmInline
value class Name(private val value: String) {
    init { require(isValid(value)) { "Invalid name" } }
    override fun toString() = value
    companion object {
        fun isValid(value: String) =
            value.isNotEmpty() && value.all { it.isLetterOrDigit() }
    }
}

typealias GameStorage = Storage<Name,Game>

open class Clash(val st: GameStorage)

class ClashRun(
    st: GameStorage,
    val game: Game,
    val sidePlayer: Player,
    val id: Name,
) : Clash(st)

private fun Clash.removeIfStarted() {
    if (this is ClashRun && sidePlayer==Player.X)
        st.delete(this.id)
}

fun Clash.start(id : Name): Clash {
    removeIfStarted()
    val game = Game(firstPlayer = Player.X).new()
    st.create(id,game)
    return ClashRun(st, game, Player.X, id)
}

fun Clash.join(id : Name): Clash {
    removeIfStarted()
    val game = requireNotNull(st.read(id)) { "Clash not started" }
    return ClashRun(st, game, Player.O, id)
}

fun Clash.exit() {
    removeIfStarted()
}

private fun Clash.runOper( oper: ClashRun.()->Game ): Clash {
    check(this is ClashRun) { "Clash not started" }
    return ClashRun(st,oper(),sidePlayer,id)
}

class NoModificationException(name: Name): IllegalStateException("No modification on $name")
class GameDeletedException(name: Name): IllegalStateException("Game $name deleted")

fun Clash.refresh() = runOper {
    (st.read(id) ?: throw GameDeletedException(id))
        .also { if (game == it) throw NoModificationException(id) }
}

fun Clash.newBoard() = runOper {
    game.new().also { st.update(id,it) }
}

fun Clash.play(pos: Position) = runOper {
    game.play(pos).also {
        check(sidePlayer==(game.board as BoardRun).turn) { "Not your turn" }
        st.update(id,it)
    }
}

val Clash.isSideTurn: Boolean get() =
    (this is ClashRun) && sidePlayer == when(game.board) {
        is BoardRun -> game.board.turn
        else -> game.firstPlayer
    }
