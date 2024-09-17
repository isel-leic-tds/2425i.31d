import java.lang.IllegalArgumentException
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
}