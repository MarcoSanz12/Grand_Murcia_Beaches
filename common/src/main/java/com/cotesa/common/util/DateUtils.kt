package com.cotesa.common.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object Factory {

        var DAY_MONTH_YEAR_DOT= "dd.MM.yyyy"
        var DAY_MONTH_YEAR_BAR= "dd/MM/yyyy"
        var DAY_MONTH_YEAR_FORMAT = "dd MMMM yyyy"
        var DATE_EVENT_FORMAT= "EEE dd MMM yyyy, hh:mm"

        fun isBetween(
            calendarStart: Calendar,
            calendarFinish: Calendar,
            calendarInit: Calendar,
            calendarEnd: Calendar
        ): Boolean {
            calendarInit[Calendar.HOUR_OF_DAY] = 0
            calendarInit[Calendar.MINUTE] = 0
            calendarInit[Calendar.SECOND] = 0
            calendarEnd[Calendar.HOUR_OF_DAY] = 23
            calendarEnd[Calendar.MINUTE] = 59
            calendarEnd[Calendar.SECOND] = 59
            return (calendarFinish.before(calendarEnd) && calendarFinish.after(calendarInit)
                    || calendarStart.after(calendarInit) && calendarStart.before(calendarEnd)
                    || calendarFinish.after(calendarEnd) && calendarStart.before(calendarInit))
        }
        fun getFormatedDate(date: Date?, format: String?): String? {
            return SimpleDateFormat(format, Locale.getDefault()).format(date)
        }

        fun fromMillisToTimeFormated(millis: Long?, dateFormat: String) : String {
            return if (millis != null) {
                val format = SimpleDateFormat(dateFormat, Locale.getDefault())
                format.format(millis)
            } else ""
        }
    }
}