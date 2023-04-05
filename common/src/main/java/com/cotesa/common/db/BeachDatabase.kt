package com.cotesa.common.db

import BeachDAO
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cotesa.common.db.converter.Converters
import com.cotesa.common.entity.beach.Beach
import javax.inject.Singleton

@Singleton
@Database(entities = [Beach::class], version = 1)
@TypeConverters(Converters::class)
abstract class BeachDatabase : RoomDatabase () {
    abstract fun beachDao() : BeachDAO
}