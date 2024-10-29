package isel.leic.tds.ttt.ui

import isel.leic.tds.ttt.model.*

abstract class Command(val syntaxArgs: String = "") {
    open fun execute(args: List<String>, game: Game): Game = game
    open val toTerminate: Boolean = false
}

object PlayCommand: Command("<position>") {
    override fun execute(args: List<String>, game: Game): Game {
        val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
        return game.play(arg.toPosition())
    }
}

fun getCommands() = mapOf(
    "PLAY" to PlayCommand,
    "NEW" to object : Command() {
        override fun execute(args: List<String>, game: Game) =
            game.new()
    },
    "EXIT" to object : Command() { override val toTerminate = true },
    "SCORE" to object : Command() {
        override fun execute(args: List<String>, game: Game) =
            game.also { it.showScore() }
    },
    //"SAVE" to ...,
    //"LOAD" to ...,
)
