private const val GREGORIAN_START_YEAR = 1582
private const val YEAR_LIMIT = 2200
private val daysOfMonth = listOf(31,28,31,30,31,30,31,31,30,31,30,31)
private val MONTHS_IN_YEAR = daysOfMonth.size

class Date(val year: Int, val month: Int = 1, val day: Int = 1): Any() {
    init {
        require(year in GREGORIAN_START_YEAR..YEAR_LIMIT){ "Invalid year $year" }
        require(month in 1..MONTHS_IN_YEAR) { "Invalid month $month" }
        require(day in 1..lastDayOfMonth) { "Invalid day $day" }
    }
    override fun equals(other: Any?) =
        other is Date && year==other.year && month==other.month && day==other.day
    override fun hashCode() =
        (((year shl 4) + month) shl 5) + day
    override fun toString() =
        "%4d-%02d-%02d".format(year,month,day)
        //"$year-${(if (month<10) "0" else "") + month }-${(if (day<10) "0" else "") + day}"
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

operator fun Date.compareTo(dt: Date) = when {
    year != dt.year -> year - dt.year
    month != dt.month -> month - dt.month
    else -> day - dt.day
}