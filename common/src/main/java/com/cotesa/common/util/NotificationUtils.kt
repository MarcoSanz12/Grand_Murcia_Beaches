package com.cotesa.common.util

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.cotesa.common.publisher.NotificationPublisher
import java.util.*

class NotificationUtils {
    companion object Factory {

        fun isInDay(context: Context, anInt: Int): Boolean {
            val timeNotify: Long =
                context.getSharedPreferences(
                    context.packageName + "_preferences",
                    Context.MODE_PRIVATE
                ).getLong(anInt.toString(), -1L)
            val cNotify = Calendar.getInstance()
            if (timeNotify == -1L) {
                return false
            }
            cNotify.timeInMillis = timeNotify
            val cActual = Calendar.getInstance()
            return cNotify[Calendar.DAY_OF_MONTH] == cActual[Calendar.DAY_OF_MONTH] && cNotify[Calendar.MONTH] == cActual[Calendar.MONTH] && cNotify[Calendar.YEAR] == cActual[Calendar.YEAR]
        }
    }
}