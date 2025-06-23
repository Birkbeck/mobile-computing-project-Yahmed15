package com.example.culinarycompanion.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.culinarycompanion.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}