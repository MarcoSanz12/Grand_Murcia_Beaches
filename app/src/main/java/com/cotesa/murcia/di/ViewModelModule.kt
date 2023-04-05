package com.cotesa.murcia.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cotesa.appcore.di.viewmodel.ViewModelFactory
import com.cotesa.appcore.di.viewmodel.ViewModelKey
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.feature.splash.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(homViewmodel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}