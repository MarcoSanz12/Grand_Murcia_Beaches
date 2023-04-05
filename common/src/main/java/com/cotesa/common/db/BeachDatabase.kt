package com.cotesa.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cotesa.common.db.converter.Converters
import com.cotesa.common.db.dao.BeachDAO
import com.cotesa.common.entity.beach.Beach

@Database(entities = [Beach::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class BeachDatabase : RoomDatabase () {
    abstract fun beachDao() : BeachDAO
}