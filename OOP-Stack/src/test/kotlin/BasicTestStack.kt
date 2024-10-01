import kotlin.test.*
import linkedlist.Stack

class BasicTestStack {
    @Test
    fun `using imutable stack`() {
        val sut = Stack<Char>().push('A').push('B')
        assertFalse(sut.isEmpty())
        assertEquals('B',sut.top)
        var stk = sut
        while (!stk.isEmpty()) {
            println(stk.top)
            stk = stk.pop()
        }
        assertTrue(stk.isEmpty())
        assertFailsWith<NoSuchElementException> { stk.top }
        assertFalse(sut.isEmpty())
        assertEquals('B',sut.top)
    }
    @Test fun `using three imutable stacks`() {
        val empty = Stack<Int>()
        val stk1 = empty.push(27)
        val stk2 = stk1.push(42)
        assertTrue(empty.isEmpty())
        assertFalse(stk1.isEmpty())
        assertEquals(27,stk1.top)
        assertEquals(42,stk2.top)
    }
    @Test fun `using pop2`() {
        val sut = Stack<Char>().push('A').push('B')
        val (val1,stk1) = sut.pop2()
/*
        val res: Pair<Char,Stack<Char>> = sut.pop2()
        val val1 = res.component1()
        val stk1 = res.component2()
*/
        assertEquals('B',val1)
        assertEquals('A',stk1.top)
    }
    @Test fun `iterate stack elements`() {
        val sut = Stack<Char>().push('A').push('B').push('C')
        sut.forEach { println(it) } // -> C B A
        val lst = sut.map { it.code }
        assertEquals(listOf(67,66,65),lst)
    }
}