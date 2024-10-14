package isel.leic.tds.ttt.ui

import isel.leic.tds.ttt.model.*

abstract class Command(val syntaxArgs: String = "") {
    open fun execute(args: List<String>, board: Board?): Board? = board
    open val toTerminate: Boolean = false
}

object PlayCommand: Command("<position>") {
    override fun execute(args: List<String>, board: Board?): Board {
        val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
        val pos = requireNotNull(arg.toIntOrNull()) {"Ivalid position $arg"}
        check(board!=null) { "No board" }
        return board.play(pos)
    }
}

fun getCommands() = mapOf(
    "PLAY" to PlayCommand,
    "NEW" to object : Command() {
        override fun execute(args: List<String>, board: Board?) =
            Board(turn = 'X')
    },
    "EXIT" to object : Command() { override val toTerminate = true }
)
