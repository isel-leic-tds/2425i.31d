package isel.leic.tds.ttt.ui

import isel.leic.tds.ttt.model.*

abstract class Command {
    open fun syntax(args: List<String>): Boolean = args.isEmpty()
    abstract fun execute(args: List<String>, board: Board?): Board
    open fun toTerminate(): Boolean = false
}

class PlayCommand: Command() {
    override fun syntax(args: List<String>): Boolean =
        args.size == 1 && args[0].toIntOrNull() != null

    override fun execute(args: List<String>, board: Board?): Board {
        val pos = args[0].toInt()
        check(board!=null) { "No board" }
        return board.tryPlay(pos)
    }
}

class NewCommand: Command() {
    override fun execute(args: List<String>, board: Board?): Board =
        Board(turn = 'X')
}
