package com.example.culinarycompanion.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val username: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    val email: String,
    val password: String
)