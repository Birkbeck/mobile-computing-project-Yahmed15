package com.example.culinarycompanion.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Setting(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "notifications_enabled") val notificationsEnabled: Boolean = true,
    @ColumnInfo(name = "theme") val theme: String = "Light"
)