package com.example.culinarycompanion

import androidx.lifecycle.LiveData
import com.example.culinarycompanion.data.RecipeDao
import com.example.culinarycompanion.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insert(recipe: Recipe) = recipeDao.insert(recipe)
    suspend fun update(recipe: Recipe) = recipeDao.update(recipe)
    suspend fun delete(recipe: Recipe) = recipeDao.delete(recipe)
}