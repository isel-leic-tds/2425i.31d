package isel.leic.tds.ttt.ui

data class LineCommand(val name: String, val args: List<String>)

tailrec fun readLineCommand(): LineCommand {
    print("> ")
    val line = readln().trim().uppercase().split(' ')
    return if (line.isNotEmpty())
        LineCommand(line[0],line.drop(1))
    else readLineCommand()
}