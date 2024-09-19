
import kotlin.test.*

class CreateDates {
    @Test
    fun CreateOneDate() {
        val sut = Date(2024,9,17)
        assertEquals(2024, sut.year,"Error on year")
        assertEquals(9,sut.month)
        assertEquals(17,sut.day)
    }
    @Test
    fun `Create date without a day`() {
        val sut = Date(2024,8)
        assertEquals(1,sut.day)
    }
    @Test
    fun `Create date without day and month`() {
        val sut = Date(2024)
        assertEquals(1,sut.month)
        assertEquals(1,sut.day)
    }
    @Test
    fun `Date is in a leap year`() {
        val sut = Date(2020)
        assertTrue(sut.isLeapYear)
        val sut2 = Date(2023)
        assertFalse(sut2.isLeapYear)
        assertTrue(2020.isLeapYear)
        assertFalse(2023.isLeapYear)
    }
    @Test
    fun `Get last day of month`() {
        val sut = Date(2014,8,3)
        assertEquals(31,sut.lastDayOfMonth)
        val sut2 = Date(2020,2)
        assertEquals(29,sut2.lastDayOfMonth)
        val sut3 = Date(2023,2)
        assertEquals(28,sut3.lastDayOfMonth)
        val ex = assertFailsWith<IllegalArgumentException> {
            val sut4 = Date(2024,0)
            assertEquals(30, sut4.lastDayOfMonth)
        }
        assertEquals("Invalid month 0",ex.message)
    }
    @Test
    fun `Add days to a date`() {
        val sut: Date = Date(2024,9,19) + 4
        assertEquals(2024,sut.year)
        assertEquals(9,sut.month)
        assertEquals(23,sut.day)
        val sut2 = Date(2024,9,19) + 30
        assertEquals(2024,sut2.year)
        assertEquals(10,sut2.month)
        assertEquals(19,sut2.day)
        val sut3 = Date(2024,12,31) +1
        assertEquals(2025,sut3.year)
        assertEquals(1,sut3.month)
        assertEquals(1,sut3.day)
    }
    @Test
    fun `compare two dates if equals`() {
        val sut1 = Date(2024,9,19)
        val sut2 = Date(2024,9,18) + 1
        // a==b --> a.equals(b)
        assertTrue(sut1==sut2)
        assertTrue(sut1.hashCode()==sut2.hashCode())
        val sut3 = sut1 + 2
        assertFalse(sut1==sut3)
        assertFalse(sut1.hashCode()==sut3.hashCode())
        //assertTrue( Date(2025,1,1) > sut1 )
        assertFalse(sut1.equals("abc"))
        assertFalse(sut1.equals(null))
    }
    @Test
    fun `compare two dates`() {
        val sut1 = Date(2024,9,19)
        val sut2 = Date(2024,9,19) + 1
        assertTrue(sut1 < sut2)
        // a > b  ->  a.compareTo(b) > 0
        // a >= b ->  a.compareTo(b) >= 0
    }
    @Test
    fun `convert Date to String`() {
        val sut = Date(2024,9,3)
        assertEquals("2024-09-03",sut.toString())
        println(sut)
    }
}