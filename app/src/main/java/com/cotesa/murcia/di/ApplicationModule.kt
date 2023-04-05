package com.cotesa.murcia.di

import android.content.Context
import com.cotesa.murcia.BeachApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BeachApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

}
