package com.example.culinarycompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val ingredients: String,
    val instructions: String,
    val category: String,
    // track whether this recipe is marked favorite by the current user
    var isFavorite: Boolean = false
)