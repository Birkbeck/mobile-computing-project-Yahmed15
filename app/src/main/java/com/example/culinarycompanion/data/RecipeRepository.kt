// app/src/main/java/com/example/culinarycompanion/data/RecipeRepository.kt
package com.example.culinarycompanion.data

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val dao: RecipeDao) {

    fun getAllRecipes(): Flow<List<Recipe>> =
        dao.getAll()

    fun getRecipeById(id: Long): Flow<Recipe> =
        dao.getById(id)

    suspend fun insert(recipe: Recipe) =
        dao.insert(recipe)

    suspend fun update(recipe: Recipe) =
        dao.update(recipe)

    suspend fun delete(recipe: Recipe) =
        dao.delete(recipe)
}