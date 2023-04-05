package com.cotesa.appcore.extension

import java.util.*
import java.util.concurrent.TimeUnit

fun Calendar.dayBetween(calendar: Calendar): Long{
    val msDiff: Long = calendar.timeInMillis -this.timeInMillis
   return TimeUnit.MILLISECONDS.toDays(msDiff)
}