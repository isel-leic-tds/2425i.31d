import kotlin.test.*

class BasicTestStack {
    @Test
    fun `test stack`() {
        val stk = MutableStack<Char>()
        assertTrue(stk.isEmpty())
        stk.push('A')
        assertFalse(stk.isEmpty())
        assertEquals('A', stk.top)
        stk.push('B')
        assertEquals('B', stk.top)
        while (!stk.isEmpty()) {
            println(stk.pop()) // -> B, A
        }
        assertTrue(stk.isEmpty())
    }
}