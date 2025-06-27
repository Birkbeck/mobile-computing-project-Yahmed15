package com.example.culinarycompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.data.RecipeRepository

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
  // LiveData of all recipes
  val allRecipes = repository.getAllRecipes().asLiveData()

  // LiveData for a single recipe
  fun getRecipe(id: Long) = repository.getRecipeById(id).asLiveData()

  // wrappers around suspend functions:
  suspend fun insert(recipe: Recipe) = repository.insert(recipe)
  suspend fun update(recipe: Recipe) = repository.update(recipe)
  suspend fun delete(recipe: Recipe) = repository.delete(recipe)
}
