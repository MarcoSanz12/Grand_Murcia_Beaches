package com.cotesa.common.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.cotesa.common.R

fun String.sendEmail(context: Context){
    val intent = Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        // (Optional) Here we're setting the title of the content
        putExtra(Intent.EXTRA_TITLE, context.getString(R.string.elige_email))
        putExtra(Intent.EXTRA_EMAIL, arrayOf(this@sendEmail))
        type = "message/rfc822"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }, null)
    context.startActivity(intent)
}
fun String.callPhone(context: Context){
    context.startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", this, null)))
}