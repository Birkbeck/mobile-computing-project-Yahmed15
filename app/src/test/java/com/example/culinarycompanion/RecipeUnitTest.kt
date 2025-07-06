package com.example.culinarycompanion

import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.util.createRecipeIfValid
import org.junit.Assert.*
import org.junit.Test

class RecipeUnitTest {

    @Test
    fun testValidRecipeCreation() {
        val recipe = createRecipeIfValid(
            name = "Pasta",
            ingredients = "Noodles, Sauce",
            instructions = "Boil water, cook noodles, add sauce",
            category = "Dinner"
        )

        assertNotNull(recipe)
        assertEquals("Pasta", recipe?.name)
        assertEquals("Noodles, Sauce", recipe?.ingredients)
        assertEquals("Boil water, cook noodles, add sauce", recipe?.instructions)
        assertEquals("Dinner", recipe?.category)
    }

    @Test
    fun testEmptyNameReturnsNull() {
        val recipe = createRecipeIfValid(
            name = "   ",
            ingredients = "Flour",
            instructions = "Mix and bake",
            category = "Dessert"
        )

        assertNull(recipe)
    }
}