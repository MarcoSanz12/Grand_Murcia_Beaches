package com.cotesa.murcia.navigator

import android.content.Intent
import android.util.Log
import com.cotesa.appcore.extension.start
import com.cotesa.appcore.navigator.BaseNavigator
import com.cotesa.appcore.platform.BaseActivity
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.fragment.DetailFragment
import com.cotesa.murcia.feature.home.fragment.HomeFragment
import com.cotesa.murcia.feature.home.fragment.ListFragment
import com.cotesa.murcia.feature.home.fragment.MapFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(): BaseNavigator() {

    fun showHome(activity: BaseActivity){
        if(activity !is HomeActivity){
            activity.start(HomeActivity.callingIntent(activity,0).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }else
            activity.changeFragment(HomeFragment())
    }

    fun initList(activity: BaseActivity){
        activity.changeFragment(ListFragment())

    }

    fun initMap(activity: BaseActivity){
        activity.changeFragment(MapFragment())
    }

    fun initDetail(activity: BaseActivity){
        activity.changeFragment(DetailFragment())
    }
    fun initHome(activity: BaseActivity){
       showHome(activity)
    }

}


