package com.example.culinarycompanion.data

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getAllRecipes(): Flow<List<Recipe>> =
        recipeDao.getAll()

    fun getRecipeById(id: Long): Flow<Recipe> =
        recipeDao.getById(id)

    fun getRecipesByCategory(category: String): Flow<List<Recipe>> =
        recipeDao.getByCategory(category)

    fun searchRecipes(query: String): Flow<List<Recipe>> =
        recipeDao.searchRecipes("%$query%")  // Wildcards for partial matches

    suspend fun insert(recipe: Recipe) =
        recipeDao.insert(recipe)

    suspend fun update(recipe: Recipe) =
        recipeDao.update(recipe)

    suspend fun delete(recipe: Recipe) =
        recipeDao.delete(recipe)

    fun getFavorites(): Flow<List<Recipe>> =
        recipeDao.getFavorites()

    suspend fun setFavorite(id: Long, fav: Boolean) =
        recipeDao.setFavorite(id, fav)
}