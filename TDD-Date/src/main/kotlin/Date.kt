class Date(val year: Int,val month: Int = 1, val day: Int = 1) {
    init {
        require(year in 1582..2200){ "Invalid year $year" }
        require(month in 1..12) { "Invalid month $month" }
        require(day in 1..lastDayOfMonth) { "Invalid day $day" }
    }
//    constructor(y: Int, m: Int) :this(y,m,1)
//    constructor(y: Int) :this(y,1,1)
    //fun isLeapYear() = (year%4==0 && year%100!=0) || year%400==0
    //val isLeapYear: Boolean = (year%4==0 && year%100!=0) || year%400==0
    //val isLeapYear get() = (year%4==0 && year%100!=0) || year%400==0
}

val Date.isLeapYear get() = year.isLeapYear
val Int.isLeapYear get() = (this%4==0 && this%100!=0) || this%400==0

private val daysOfMonth = listOf(31,28,31,30,31,30,31,31,30,31,30,31)

val Date.lastDayOfMonth: Int
    get() = if (month==2 && isLeapYear) 29 else daysOfMonth[month-1]