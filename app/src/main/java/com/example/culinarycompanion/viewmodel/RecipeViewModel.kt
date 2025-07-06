package com.example.culinarycompanion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.culinarycompanion.data.AppDatabase
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.data.RecipeRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: RecipeRepository

  val allRecipes: LiveData<List<Recipe>>

  init {
    val dao = AppDatabase.getInstance(application).recipeDao()
    repository = RecipeRepository(dao)

    allRecipes = repository.getAllRecipes()
      .asLiveData(viewModelScope.coroutineContext)
  }

  fun insert(recipe: Recipe) = viewModelScope.launch {
    repository.insert(recipe)
  }

  fun update(recipe: Recipe) = viewModelScope.launch {
    repository.update(recipe)
  }

  fun delete(recipe: Recipe) = viewModelScope.launch {
    repository.delete(recipe)
  }

  fun setFavorite(id: Long, fav: Boolean) = viewModelScope.launch {
    repository.setFavorite(id, fav)
  }

  fun searchRecipes(query: String): LiveData<List<Recipe>> {
    return repository.searchRecipes(query)
      .asLiveData(viewModelScope.coroutineContext)
  }

  fun getRecipeById(id: Long): LiveData<Recipe> {
    return repository.getRecipeById(id)
      .asLiveData(viewModelScope.coroutineContext)
  }
}