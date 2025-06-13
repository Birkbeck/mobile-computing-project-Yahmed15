package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.activity_recipe_detail)

        val dao = AppDatabase.getInstance(this).recipeDao()
        val id  = intent.getIntExtra("id", -1)

        // Views
        val titleTV  = findViewById<TextView>(R.id.tvDetailRecipeTitle)
        val catTV    = findViewById<TextView>(R.id.tvDetailCategory)
        val ingTV    = findViewById<TextView>(R.id.tvDetailIngredients)
        val instrTV  = findViewById<TextView>(R.id.tvDetailInstructions)
        val editBtn  = findViewById<Button>(R.id.btnDetailEdit)
        val deleteBtn= findViewById<Button>(R.id.btnDetailDelete)

        // Load recipe once
        dao.getAll().observe(this) { list ->
            list.find { it.id == id }?.let { r ->
                titleTV.text = r.name
                catTV.text   = r.category
                ingTV.text   = r.ingredients
                instrTV.text = r.instructions

                editBtn.setOnClickListener {
                    Intent(this, EditRecipeActivity::class.java).apply {
                        putExtra("id", r.id)
                    }.also(::startActivity)
                }
                deleteBtn.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("Delete Recipe?")
                        .setMessage("This cannot be undone.")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Delete") { _, _ ->
                            lifecycleScope.launch { dao.delete(r) }
                            finish()
                        }
                        .show()
                }
            }
        }
    }
}