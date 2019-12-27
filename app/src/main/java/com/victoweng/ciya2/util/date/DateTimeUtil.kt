package com.victoweng.ciya2.util.date

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateTimeUtil {

    fun getCalendarInstance() = Calendar.getInstance()

    fun asString(date: Date) : String {
        val dateFormat = SimpleDateFormat("EEE MMM dd yy 'at' h:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getMinDate() = System.currentTimeMillis() - 1000

    fun getCurrentTime() = GregorianCalendar().time

}
