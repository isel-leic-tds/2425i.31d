package isel.leic.tds.ttt.ui

import isel.leic.tds.ttt.model.*

class Command(
    val syntaxArgs: String = "",
    val toTerminate: Boolean = false,
    val execute: (args: List<String>, clash: Clash)-> Clash = { _, clash -> clash }
)

val playCommand = Command("<position>") { args, clash ->
    val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
    clash.play(arg.toPosition())
}

fun nameCmd(fx: Clash.(Name)-> Clash) = Command("<name>") { args, clash ->
    val arg = requireNotNull(args.firstOrNull()) { "Missing name" }
    clash.fx(Name(arg))
}

fun getCommands() = mapOf(
    "PLAY" to playCommand,
    "NEW" to Command { _, clash -> clash.newBoard() },
    "EXIT" to Command(toTerminate = true),
    "SCORE" to Command { _, clash -> clash.also { it.showScore() } },
    "REFRESH" to Command { _, clash -> clash.refresh() },
    "START" to nameCmd(Clash::start),
    "JOIN" to nameCmd(Clash::join)
)
