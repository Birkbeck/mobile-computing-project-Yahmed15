package com.example.culinarycompanion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    /** Insert a new user. Aborts if username/email already exist. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    /** Authenticate against the `users` table. */
    @Query("""
      SELECT * FROM users
      WHERE username = :username 
        AND password = :password
      LIMIT 1
    """)
    suspend fun authenticate(username: String, password: String): User?

    /** Lookup by primary‐key ID. */
    @Query("""
      SELECT * FROM users
      WHERE id = :userId
      LIMIT 1
    """)
    suspend fun getById(userId: Long): User?
}