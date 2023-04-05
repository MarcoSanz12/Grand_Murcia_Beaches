package com.cotesa.murcia

import android.app.*
import androidx.lifecycle.*
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.di.ApplicationModule
import com.cotesa.murcia.di.DaggerApplicationComponent
import com.cotesa.murcia.navigator.Navigator
import java.util.*

class BeachApplication : Application(),
    LifecycleObserver, DefaultLifecycleObserver {
    var navigator: Navigator = Navigator()

    private val locale: Locale? = null

    val appComponent: ApplicationComponent by lazy(LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
    private var isAppInForeground = false

    override fun onCreate() {
        super<Application>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        onForegroundStart()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        onForegroundStop()
    }

    fun onForegroundStart() {
        isAppInForeground = true
    }

    fun onForegroundStop() {
        isAppInForeground = false
    }

//    fun printHashKey() {
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                val info = packageManager.getPackageInfo(
//                    "com.cotesa.Gestion",
//                    PackageManager.GET_SIGNING_CERTIFICATES
//                )
//                for (signature in info.signingInfo) {
//                    val md = MessageDigest.getInstance("SHA")
//                    md.update(signature.toByteArray())
//                    Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//                }
//            } else {
//                //
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
////
//        } catch (e: NoSuchAlgorithmException) {
////
//        }
//    }

}