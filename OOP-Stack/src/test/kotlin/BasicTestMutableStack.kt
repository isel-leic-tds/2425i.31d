import kotlin.test.*
import linkedlist.*

class BasicTestMutableStack {
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
    @Test
    fun `empty stack conditions`() {
        val sut = MutableStack<String>()
        assertFailsWith<NoSuchElementException> { sut.pop() }
        assertFailsWith<NoSuchElementException> { sut.top }
        assertTrue(sut.isEmpty())
    }
    @Test fun `not empty stack conditiions`() {
        val sut = MutableStack<Int>()
        sut.push(27)
        assertEquals(27, sut.top)
        assertFalse(sut.isEmpty())
        assertEquals(27,sut.pop())
        assertTrue(sut.isEmpty())
    }
    @Test fun `test equality`() {
        val sut1 = MutableStack<Int>()
        sut1.push(27)
        sut1.push(32)
        val sut2 = MutableStack<Int>()
        sut2.push(27)
        sut2.push(32)
        assertTrue( sut1 == sut2 )
        // A==B  ->  A.equals(B)
    }
}