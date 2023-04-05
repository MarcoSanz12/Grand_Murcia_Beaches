package com.cotesa.murcia.navigator

import android.content.Intent
import android.util.Log
import com.cotesa.appcore.extension.start
import com.cotesa.appcore.navigator.BaseNavigator
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.fragment.MenuFragment
import com.cotesa.murcia.feature.home.fragment.ListFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(): BaseNavigator() {

    fun showHome(activity: BaseActivity){
        if(activity !is HomeActivity){
            activity.start(HomeActivity.callingIntent(activity,0).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }else
            activity.changeFragment(MenuFragment())
    }

    fun initList(activity: BaseActivity){
        if (activity is HomeActivity){
            Log.e("Navigator","Cambio fragment")
            activity.changeFragment(ListFragment())

        }
    }
    fun initHome(activity: BaseActivity){
       showHome(activity)
    }

}


