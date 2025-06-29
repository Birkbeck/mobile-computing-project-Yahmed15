package com.example.culinarycompanion.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.data.RecipeDatabase
import com.example.culinarycompanion.data.RecipeRepository

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

  // LiveData of all recipes
  val allRecipes: LiveData<List<Recipe>> = repository.getAllRecipes().asLiveData()

  // LiveData for a single recipe
  fun getRecipe(id: Long): LiveData<Recipe> =
    repository.getRecipeById(id).asLiveData()

  // LiveData for a category
  fun filterBy(category: String): LiveData<List<Recipe>> =
    repository.getRecipesByCategory(category).asLiveData()

  // wrappers around suspend functions:
  suspend fun insert(recipe: Recipe) = repository.insert(recipe)
  suspend fun update(recipe: Recipe) = repository.update(recipe)
  suspend fun delete(recipe: Recipe) = repository.delete(recipe)

  /** ViewModelProvider.Factory so we can pass in our Repository */
  class Factory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
        val dao = RecipeDatabase.getInstance(context).recipeDao()
        val repo = RecipeRepository(dao)
        return RecipeViewModel(repo) as T
      }
      throw IllegalArgumentException("Unknown ViewModel class")
    }
  }
}