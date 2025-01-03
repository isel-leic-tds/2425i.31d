import kotlin.test.*

import isel.leic.tds.ttt.model.*

class SerializerTests {
    @Test fun `serialize and deserialize BoardRun`() {
        val board = playSequence(4,0,5)
        val text = BoardSerializer.serialize(board)
        assertEquals("RUN O | 4:X 0:O 5:X", text)
        val otherBoard = BoardSerializer.deserialize(text)
        assertEquals(board, otherBoard)
    }
    @Test fun `serialize and deserialize BoardDraw`() {
        val board = playSequence(0, 1, 2, 3, 5, 4, 6, 8, 7)
        val text = BoardSerializer.serialize(board)
        assertEquals("DRAW  | 0:X 1:O 2:X 3:O 5:X 4:O 6:X 8:O 7:X", text)
        val otherBoard = BoardSerializer.deserialize(text)
        assertEquals(board, otherBoard)
    }
    @Test fun `serialize and deserialize BoardWin`() {
        val board = playSequence(1, 0, 4, 3, 2, 5, 7)
        val text = BoardSerializer.serialize(board)
        assertEquals("WIN X | 1:X 0:O 4:X 3:O 2:X 5:O 7:X", text)
        val otherBoard = BoardSerializer.deserialize(text)
        assertEquals(board, otherBoard)
    }
    @Test fun `serialize and deserialize Game`() {
        val game = Game(
            board = playSequence(4, 0, 5, 1),
            firstPlayer = Player.X,
            score = mapOf(Player.X to 2, Player.O to 1, null to 4)
        )
        val text = GameSerializer.serialize(game)
        val expected = """
            X:2 O:1 null:4
            X
            RUN X | 4:X 0:O 5:X 1:O
        """.trimIndent()
        assertEquals(expected, text.trim())
        val otherGame = GameSerializer.deserialize(text)
        assertEquals(game, otherGame)
    }
}