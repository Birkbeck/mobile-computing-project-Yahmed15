package com.example.culinarycompanion.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    @ColumnInfo(name = "username") val username: String,

    @ColumnInfo(name = "full_name") val fullName: String,

    @ColumnInfo(name = "email") val email: String,

    @ColumnInfo(name = "password") val password: String
)