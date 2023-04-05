package com.cotesa.common.publisher

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.cotesa.appcore.extension.putAny
import com.cotesa.common.util.Constant.Factory.CHANNEL_BEACONS
import java.util.*

class NotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = intent.extras!![NOTIFICATION] as Notification?
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(mNotificationManager)

        val pref = context.getSharedPreferences(
            context.packageName + "_preferences",
            Context.MODE_PRIVATE
        )
        pref.putAny(
            intent.extras!!.getInt(NOTIFICATION_ID, -1).toString(),
            Calendar.getInstance().timeInMillis
        )

//        if (pref.getBoolean(Constant.NOTIFICATION_ACTIVATED, true)){
            if (!isVisibleNotification(
                mNotificationManager, intent.extras!!
                    .getInt(NOTIFICATION_ID, 0)
            )) {
//            TurismoApplication.getInstance().getRepository(SlotRepository::class.java)
//                .insertSlotDetected(
//                    intent.extras!!.getInt(NOTIFICATION_ID, 0)
//                )
            mNotificationManager.notify(
                intent.extras!!.getInt(NOTIFICATION_ID, 0), notification
            )}
        }
//    }

    private fun isVisibleNotification(
        mNotificationManager: NotificationManager,
        anInt: Int
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (notification in mNotificationManager.activeNotifications) {
                if (notification.id == anInt) {
                    return true
                }
            }
            false
        } else false
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // You should create a String resource for this instead of storing in a variable
            val mChannel = NotificationChannel(
                CHANNEL_BEACONS,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mChannel.description = "This is default channel used for all other notifications"
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        const val NOTIFICATION_ID = "notification-id"
        const val NOTIFICATION = "notification"
    }
}