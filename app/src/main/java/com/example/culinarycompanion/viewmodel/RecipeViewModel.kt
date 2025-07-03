package com.example.culinarycompanion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.culinarycompanion.data.AppDatabase
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(app: Application) : AndroidViewModel(app) {
  private val repo = RecipeRepository(AppDatabase.getInstance(app).recipeDao())

  val allRecipes: LiveData<List<Recipe>> = repo.getAllRecipes().asLiveData()
  fun filterBy(category: String): LiveData<List<Recipe>> =
    repo.getRecipesByCategory(category).asLiveData()
  fun getRecipe(id: Long): LiveData<Recipe> =
    repo.getRecipeById(id).asLiveData()
  fun getFavorites(): LiveData<List<Recipe>> =
    repo.getFavorites().asLiveData()

  fun insert(r: Recipe) = viewModelScope.launch { repo.insert(r) }
  fun update(r: Recipe) = viewModelScope.launch { repo.update(r) }
  fun delete(r: Recipe) = viewModelScope.launch { repo.delete(r) }
  fun setFavorite(id: Long, fav: Boolean) = viewModelScope.launch {
    repo.setFavorite(id, fav)
  }
}