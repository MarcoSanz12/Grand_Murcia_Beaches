package com.cotesa.common.di

import android.content.Context
import androidx.room.Room
import com.cotesa.common.db.BeachDatabase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {
    companion object {
        const val DATABASE_NAME = "beachDB"
    }

    /**
     * Returns the database of the application.
     * @param context Context in which the application is running
     * @return the database of the application
     */
    @Singleton
    @Provides
    fun provideBeachDataBase(context: Context): BeachDatabase {
        return Room.databaseBuilder(
            context.applicationContext, BeachDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration().build()
    }
}