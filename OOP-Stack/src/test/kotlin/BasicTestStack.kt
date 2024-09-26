import kotlin.test.*

class BasicTestStack {
    @Test
    fun `using imutable stack`() {
        val sut = Stack().push('A').push('B')
        assertFalse(sut.isEmpty())
        assertEquals('B',sut.top)
        var stk = sut
        while (!stk.isEmpty()) {
            println(stk.top)
            stk = stk.pop()
        }
        assertTrue(stk.isEmpty())
        assertFailsWith<NoSuchElementException> {
            stk.top
        }
    }
}