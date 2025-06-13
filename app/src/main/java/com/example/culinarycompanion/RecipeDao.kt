package com.example.culinarycompanion

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): LiveData<List<Recipe>>

    @Insert
    suspend fun insert(recipe: Recipe): Long    // Room supports Long here

    @Update
    suspend fun update(recipe: Recipe): Int     // Room supports Int here

    @Delete
    suspend fun delete(recipe: Recipe): Int     // Room supports Int here
}