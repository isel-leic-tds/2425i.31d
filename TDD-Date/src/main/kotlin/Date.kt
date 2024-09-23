private const val GREGORIAN_START_YEAR = 1582
private const val YEAR_LIMIT = 2200
private val daysOfMonth = listOf(31,28,31,30,31,30,31,31,30,31,30,31)
private val MONTHS_IN_YEAR = daysOfMonth.size

private const val YEAR_BITS = 12
private const val MONTH_BITS = 4
private const val DAY_BITS = 5

@JvmInline
value class Date private constructor(private val bits: Int) {
    val year:Int get() = bits ushr (MONTH_BITS+DAY_BITS)
    val month:Int get() = (bits ushr DAY_BITS) and ((1 shl MONTH_BITS)-1)
    val day:Int get() = bits and ((1 shl DAY_BITS)-1)

    constructor(y: Int, m: Int = 1, d: Int = 1) : this(
        (y shl (MONTH_BITS+DAY_BITS)) or (m shl DAY_BITS) or d
    ) {
        require(y in GREGORIAN_START_YEAR..YEAR_LIMIT){ "Invalid year $y" }
        require(m in 1..MONTHS_IN_YEAR) { "Invalid month $m" }
        require(d in 1..lastDayOfMonth) { "Invalid day $d" }
    }
    override fun toString() =
        "%4d-%02d-%02d".format(year,month,day)
    operator fun compareTo(dt: Date) = this.bits - dt.bits
}

val Date.isLeapYear get() = year.isLeapYear
val Int.isLeapYear get() = (this%4==0 && this%100!=0) || this%400==0

val Date.lastDayOfMonth: Int
    get() = if (month==2 && isLeapYear) 29 else daysOfMonth[month-1]

operator fun Date.plus(days: Int): Date = addDays(days)
operator fun Int.plus(date: Date): Date = date.addDays(this)

tailrec fun Date.addDays(days: Int): Date {
    val d = days+day
    if (d <= lastDayOfMonth) return Date(year, month, d)
    val dt = if (month < MONTHS_IN_YEAR) Date(year, month + 1, 1)
    else Date(year + 1, 1, 1)
    return dt.addDays(d - lastDayOfMonth -1)
}
