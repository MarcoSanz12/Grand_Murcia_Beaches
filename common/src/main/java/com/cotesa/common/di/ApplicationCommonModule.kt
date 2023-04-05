package com.cotesa.common.di

import android.content.Context
import com.cotesa.common.BuildConfig
import com.cotesa.common.repository.BeachRepository
import com.cotesa.common.util.NetworkUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationCommonModule () {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BEACH_URL)
            .client(NetworkUtils.createClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideBeachRepository(dataSource:BeachRepository.Network) : BeachRepository = dataSource

}