package com.example.culinarycompanion.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAll(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getById(id: Long): Flow<Recipe>

    @Query("SELECT * FROM recipes WHERE category = :category")
    fun getByCategory(category: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("UPDATE recipes SET isFavorite = :fav WHERE id = :id")
    suspend fun setFavorite(id: Long, fav: Boolean)
}