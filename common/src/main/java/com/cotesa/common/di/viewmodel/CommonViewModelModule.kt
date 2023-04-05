package com.cotesa.common.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CommonViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: CommonViewModelFactory): ViewModelProvider.Factory

}