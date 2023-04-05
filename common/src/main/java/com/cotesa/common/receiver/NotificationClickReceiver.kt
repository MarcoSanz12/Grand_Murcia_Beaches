package com.cotesa.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        // You can also include some extra data.
        val intent = Intent("notification")
//        intent.putExtra("nid", p1!!.extras?.get("nid") as String)
        intent.putExtra("message", p1!!.extras?.get("message") as String)
        intent.putExtra("title", p1.extras?.get("title")as String)
        intent.putExtra("more_information", p1.extras?.get("more_information")as String)
        intent.putExtra("image", p1.extras?.get("image")as String)
        intent.putExtra("related_content", p1.extras?.get("related_content")as String)
        intent.putExtra("timestamp", p1.extras?.get("timestamp")as Long)
        LocalBroadcastManager.getInstance(p0!!.applicationContext).sendBroadcast(intent)
    }
}