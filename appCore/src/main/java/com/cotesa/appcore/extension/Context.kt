package com.cotesa.appcore.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.util.*

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.start(intent: Intent) {
    this.startActivity(intent)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.getStringByLocale(@StringRes stringRes: Int, vararg formatArgs: String): String {
    val configuration = Configuration(resources.configuration)
    val preferences = getSharedPreferences(
        packageName + "_preferences",
        Context.MODE_PRIVATE
    )
    var locale: Locale? = null
    if (preferences.contains("LANG")) {

        when (preferences.getInt("LANG", 0)) {
            0 -> locale = Locale("es", "ES")
            1 -> locale = Locale("en", "GB")
            2 -> locale = Locale("fr", "FR")
            3 -> locale = Locale("it", "IT")
            4 -> locale = Locale("de", "DE")
            5 -> locale = Locale("nl", "NL")
        }
//            val res = resources
//            val dm = res.displayMetrics
//            val conf = res.configuration
//            if (conf.locale != locale) {
//                conf.locale = locale
//                res.updateConfiguration(conf, dm)
//            }
    } else
        locale = Locale.getDefault()
    configuration.setLocale(locale)
    return createConfigurationContext(configuration).resources.getString(stringRes, *formatArgs)
}