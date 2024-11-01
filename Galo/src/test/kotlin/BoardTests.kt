import kotlin.test.*
import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.*

fun playSequence(vararg moves: Int): Board =
    moves.fold(BoardRun(Player.X) as Board) { b, idx -> b.play(Position(idx)) }

class BoardTests {
    @Test
    fun testInitialBoard() {
        val board = BoardRun(turn = Player.X)
        assertTrue(board.moves.isEmpty())
        assertEquals(Player.X, board.turn)
    }

    // All tests below assume BOARD_DIM is 3
    @Test
    fun `test winning in a column`() {
        val board = playSequence(1, 0, 4, 3, 2, 5, 7)
        //board.show()
        assertTrue(board is BoardWin)
        assertEquals(Player.X, board.winner)
    }
    @Test fun `test winning in a row`() {
        val board = playSequence(3, 0, 4, 1, 5)
        assertTrue(board is BoardWin)
        assertEquals(Player.X, board.winner)
    }
    @Test fun `test winning in a diagonal`() {
        val board = playSequence(0, 1, 4, 3, 8)
        assertTrue(board is BoardWin)
        assertEquals(Player.X, board.winner)
    }
    @Test fun `test winning in the other diagonal`() {
        val board = playSequence(2, 1, 4, 3, 6)
        assertTrue(board is BoardWin)
        assertEquals(Player.X, board.winner)
    }
    @Test fun `test draw`() {
        val board = playSequence(0, 1, 2, 3, 5, 4, 6, 8, 7)
        assertTrue(board is BoardDraw)
    }
    @Test fun `test invalid move`() {
        val board = playSequence(0, 4, 3)
        val ex = assertFailsWith<IllegalStateException> {
            board.play(Position(4))
        }
        assertEquals("Position already used", ex.message)
    }
    @Test fun `test playing after game over`() {
        val board = playSequence(2, 1, 4, 3, 6)
        assertTrue(board is BoardWin)
        val ex = assertFailsWith<IllegalStateException> {
            board.play(Position(0))
        }
        assertEquals("Game is over", ex.message)
    }
}