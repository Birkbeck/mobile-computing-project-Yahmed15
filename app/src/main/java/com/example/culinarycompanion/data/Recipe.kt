// app/src/main/java/com/example/culinarycompanion/data/Recipe.kt
package com.example.culinarycompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ingredients: String,
    val instructions: String
)