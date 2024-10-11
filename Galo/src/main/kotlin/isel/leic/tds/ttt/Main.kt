package isel.leic.tds.ttt

import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.*

fun main() {
    var board: Board? = null
    val cmds = getCommands()
    while (true) {
        val (name, args) = readLineCommand()
        val cmd = cmds[name]
        when {
            cmd==null -> println("Unknown command $name")
            !cmd.syntaxe(args) -> println("Syntax error")
            else -> board = cmd.execute(args,board)
        }
        board?.show()
    }
}