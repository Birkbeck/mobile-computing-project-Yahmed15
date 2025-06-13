package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class DashboardActivity : AppCompatActivity() {
    private lateinit var dao: RecipeDao
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        setContentView(R.layout.activity_dashboard)

        dao = AppDatabase.getInstance(this).recipeDao()

        // RecyclerView setup
        val rv = findViewById<RecyclerView>(R.id.recipeRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = RecipeAdapter(emptyList()) { r ->
            val i = Intent(this, RecipeDetailActivity::class.java).apply {
                putExtra("id", r.id)
            }
            startActivity(i)
        }
        rv.adapter = adapter

        // Observe all recipes
        dao.getAll().observe(this) { list ->
            adapter.setRecipes(list)
        }

        // New Recipe button
        findViewById<MaterialButton>(R.id.btnNewRecipe)
            .setOnClickListener { startActivity(Intent(this, AddRecipeActivity::class.java)) }

        // Category taps
        listOf(
            R.id.tvCategoryBreakfast to "Breakfast",
            R.id.tvCategoryBrunch    to "Brunch",
            R.id.tvCategoryLunch     to "Lunch",
            R.id.tvCategoryDinner    to "Dinner",
            R.id.tvCategoryDessert   to "Desserts",
            R.id.tvCategoryOther     to "Other"
        ).forEach { (id, cat) ->
            findViewById<TextView>(id).setOnClickListener {
                // Launch FilterActivity
                Intent(this, FilterActivity::class.java).also {
                    it.putExtra("filter", cat)
                    startActivity(it)
                }
            }
        }
    }
}