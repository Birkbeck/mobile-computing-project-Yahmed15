package com.example.culinarycompanion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert suspend fun insert(user: User): Long

    @Query("""
    SELECT * FROM users 
    WHERE username = :username AND password = :password 
    LIMIT 1
  """)
    suspend fun authenticate(username: String, password: String): User?
}