package com.cotesa.common.db.dao

import androidx.room.*
import com.cotesa.common.entity.beach.Beach

@Dao
interface BeachDAO {

    @Insert (entity = Beach::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertBeach(beach : Beach)

    @Query("SELECT * FROM beach_table WHERE id = :id LIMIT 1")
    fun selectBeachById(id:Int) : Beach?

    @Query("SELECT * FROM beach_table")
    fun selectBeaches():List<Beach>?

}