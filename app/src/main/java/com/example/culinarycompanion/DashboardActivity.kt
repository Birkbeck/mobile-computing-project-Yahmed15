package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culinarycompanion.databinding.ActivityDashboardBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {

    private lateinit var dao: RecipeDao
    private lateinit var adapter: RecipeAdapter
    private val allRecipes = mutableListOf<Recipe>()

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Using ViewBinding for cleaner findViewById
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Toolbar setup ---
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDashboard)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            // Home icon / logo click → scroll to top
            binding.recipeRecyclerView.smoothScrollToPosition(0)
        }
        // If you want logo itself clickable:
        toolbar.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                })
        }

        // --- RecyclerView & Adapter ---
        val rv = binding.recipeRecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        adapter = RecipeAdapter(emptyList()) { recipe ->
            startActivity(Intent(this, RecipeDetailActivity::class.java).apply {
                putExtra("id", recipe.id)
            })
        }
        rv.adapter = adapter

        // --- Room DAO & LiveData ---
        dao = AppDatabase.getInstance(this).recipeDao()
        dao.getAll().observe(this) { list ->
            allRecipes.clear()
            allRecipes.addAll(list)
            adapter.setRecipes(list)
        }

        // --- FAB: Add Recipe ---
        binding.fabAddRecipe.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }

        // --- Category filtering via Chips ---
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupCategories)
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) {
                // no filter → show all
                adapter.setRecipes(allRecipes)
            } else {
                val selectedCats = checkedIds.map { id ->
                    (group.findViewById<Chip>(id)).text.toString()
                }
                adapter.setRecipes(
                    allRecipes.filter { it.category in selectedCats }
                )
            }
        }
    }
}