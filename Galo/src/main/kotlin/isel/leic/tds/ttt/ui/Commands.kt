package isel.leic.tds.ttt.ui

import isel.leic.tds.storage.Storage
import isel.leic.tds.ttt.model.*

class Command(
    val syntaxArgs: String = "",
    val toTerminate: Boolean = false,
    val execute: (args: List<String>, game: Game)-> Game = { _,game -> game }
)

val playCommand = Command("<position>") { args, game ->
    val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
    game.play(arg.toPosition())
}

fun storageCommand(fx: (name: String, Game)->Game) = Command("<name>") { args, game ->
    val name = requireNotNull(args.firstOrNull()) {"Missing name"}
    fx(name, game)
}

fun getCommands(storage: Storage<String,Game>) = mapOf(
    "PLAY" to playCommand,
    "NEW" to Command { _, game -> game.new() },
    "EXIT" to Command(toTerminate = true),
    "SCORE" to Command { _, game -> game.also { it.showScore() } },
    "SAVE" to storageCommand { name, game -> game.also{
        if (storage.read(name)!=null) storage.update(name,game) else storage.create(name,game)
    } },
    "LOAD" to storageCommand {name, _ -> checkNotNull(storage.read(name)) }
)

/*
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
    //"SAVE"
    //"LOAD" to ...,
)
*/
