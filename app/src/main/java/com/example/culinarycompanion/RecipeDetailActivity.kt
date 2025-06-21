package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {
    private var recipeId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Toolbar & logo
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDetail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setOnClickListener {
            // Logo click → home
            startActivity(Intent(this, DashboardActivity::class.java)
                .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }

        // Views
        val tvTitle = findViewById<TextView>(R.id.tvDetailRecipeTitle)
        val tvCat   = findViewById<TextView>(R.id.tvDetailCategory)
        val tvIngr  = findViewById<TextView>(R.id.tvDetailIngredients)
        val tvInstr = findViewById<TextView>(R.id.tvDetailInstructions)
        val btnEdit = findViewById<MaterialButton>(R.id.btnDetailEdit)
        val btnDel  = findViewById<MaterialButton>(R.id.btnDetailDelete)

        // Load recipe from DB
        recipeId = intent.getLongExtra("id", -1L)
        val dao = AppDatabase.getInstance(this).recipeDao()
        lifecycleScope.launch {
            dao.getById(recipeId)?.let { r ->
                tvTitle.text = r.name
                tvCat.text   = r.category
                tvIngr.text  = r.ingredients.replace("\\n", "\n")
                tvInstr.text = r.instructions.replace("\\n", "\n")
            }
        }

        // Edit → EditRecipeActivity
        btnEdit.setOnClickListener {
            Intent(this, EditRecipeActivity::class.java)
                .putExtra("id", recipeId)
                .also(::startActivity)
        }

        // Delete with confirmation
        btnDel.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe? This cannot be undone.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    lifecycleScope.launch {
                        dao.delete(Recipe(id = recipeId, name="", category="", ingredients="", instructions=""))
                        finish()
                    }
                }
                .show()
        }
    }
}