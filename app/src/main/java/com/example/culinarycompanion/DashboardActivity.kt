package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class DashboardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // 1. Set up RecyclerView with mock data and click listener
        recyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val sampleRecipes = listOf(
            Recipe("Spaghetti Bolognese", "Dinner", "Pasta, Sauce", "Boil and serve"),
            Recipe("Pancakes", "Breakfast", "Flour, Milk, Egg", "Whisk and fry")
        )
        adapter = RecipeAdapter(sampleRecipes) { recipe ->
            // On recipe click, open detail page
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipe_name", recipe.name)
            intent.putExtra("recipe_category", recipe.category)
            intent.putExtra("recipe_ingredients", recipe.ingredients)
            intent.putExtra("recipe_instructions", recipe.instructions)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // 2. New Recipe button → AddRecipeActivity
        findViewById<MaterialButton>(R.id.btnNewRecipe).setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }

        // 3. Category taps → FilterActivity with selected category
        val categoryIds = listOf(
            R.id.tvCategoryBreakfast,
            R.id.tvCategoryBrunch,
            R.id.tvCategoryLunch,
            R.id.tvCategoryDinner,
            R.id.tvCategoryDessert,
            R.id.tvCategoryOther
        )
        categoryIds.forEach { id ->
            findViewById<TextView>(id).setOnClickListener { v ->
                val category = (v as TextView).text.toString()
                val intent = Intent(this, FilterActivity::class.java)
                intent.putExtra("filter_category", category)
                startActivity(intent)
            }
        }
    }
}