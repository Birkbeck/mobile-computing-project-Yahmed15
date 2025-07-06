package com.example.culinarycompanion.util

import com.example.culinarycompanion.data.Recipe

fun createRecipeIfValid(
    name: String,
    ingredients: String,
    instructions: String,
    category: String
): Recipe? {
    val trimmedName = name.trim()
    if (trimmedName.isEmpty()) return null

    return Recipe(
        name = trimmedName,
        ingredients = ingredients.trim(),
        instructions = instructions.trim(),
        category = category
    )
}
