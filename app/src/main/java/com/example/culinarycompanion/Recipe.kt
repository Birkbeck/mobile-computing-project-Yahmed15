package com.example.culinarycompanion

/**
 * Simple data class representing a recipe.
 */
data class Recipe(
    val name: String,
    val category: String,
    val ingredients: String,
    val instructions: String
)