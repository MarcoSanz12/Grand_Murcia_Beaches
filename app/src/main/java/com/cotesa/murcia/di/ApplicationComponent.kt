package com.cotesa.murcia.di

import com.cotesa.common.di.ApplicationCommonModule
import com.cotesa.common.di.DaoModule
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.fragment.DetailFragment
import com.cotesa.murcia.feature.home.fragment.ListFragment
import com.cotesa.murcia.feature.home.fragment.MapFragment
import com.cotesa.murcia.feature.home.fragment.HomeFragment
import com.cotesa.murcia.feature.splash.activity.SplashActivity
import com.cotesa.murcia.feature.splash.fragment.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, ApplicationCommonModule::class, DaoModule::class])
interface ApplicationComponent {
    fun inject(application: BeachApplication)
    fun inject(splashActivity: SplashActivity)
    fun inject(splashFragment: SplashFragment)

    fun inject(homeActivity: HomeActivity)

    fun inject(listFragment: ListFragment)
    fun inject(mapFragment: MapFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(detailFragment: DetailFragment)

}
