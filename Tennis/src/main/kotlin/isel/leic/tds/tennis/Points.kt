package isel.leic.tds.tennis

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30) , FORTY(40) , GAME(45)
}

fun Points.next(): Points = Points.entries[ordinal+1]

