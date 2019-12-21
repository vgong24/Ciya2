package com.victoweng.ciya2.util.date

import java.util.*

class DateBuilder {

    val calendar: GregorianCalendar by lazy {
        GregorianCalendar()
    }

    fun build(): Date {
        return calendar.time
    }

    fun day(day: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
    }

    fun month(month: Int) {
        calendar.set(Calendar.MONTH, month)
    }

    fun year(year: Int) {
        calendar.set(Calendar.YEAR, year)
    }

    fun getMonth() = calendar.get(Calendar.MONTH)

    fun getYear() = calendar.get(Calendar.YEAR)

    fun getDay() = calendar.get(Calendar.DAY_OF_MONTH)


    fun hour(hour: Int) {
        calendar.set(GregorianCalendar.HOUR_OF_DAY, hour)
    }

    fun minute(minute: Int) {
        calendar.set(GregorianCalendar.MINUTE, minute)
    }
}