package com.example.culinarycompanion.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(s: Setting)

    @Query("SELECT * FROM settings WHERE id = 1")
    fun get(): LiveData<Setting>
}