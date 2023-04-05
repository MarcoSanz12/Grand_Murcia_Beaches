package com.cotesa.appcore.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cotesa.appcore.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected = provideInternetStatus(context)

    /**
     * Checks if current device has internet access
     * if current version is superior to M (23) uses [ConnectivityManager]
     * else uses [networkInfo]
     */
    private fun provideInternetStatus(context: Context): Boolean {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {

            var cm: ConnectivityManager =
                context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

            var status = false
            if (cm != null) {
                var netCap = cm.getNetworkCapabilities(cm.activeNetwork)

                status = netCap?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            }
            return status
        } else {
            return context.networkInfo?.isConnectedOrConnecting ?: false
        }
    }
}