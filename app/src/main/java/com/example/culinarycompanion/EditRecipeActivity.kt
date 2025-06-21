package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class EditRecipeActivity : AppCompatActivity() {
    private var recipeId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        // --- Toolbar & Logo ---
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarEdit)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        findViewById<ImageView>(R.id.imgLogoEdit).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)
                .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }

        // --- Views ---
        val etName    = findViewById<TextInputEditText>(R.id.etEditRecipeName)
        val spinner   = findViewById<Spinner>(R.id.spinnerEditCategory)
        val etIngr    = findViewById<TextInputEditText>(R.id.etEditIngredients)
        val etInstr   = findViewById<TextInputEditText>(R.id.etEditInstructions)
        val btnCancel = findViewById<MaterialButton>(R.id.btnCancelEdit)
        val btnSave   = findViewById<MaterialButton>(R.id.btnSaveChanges)
        val btnDelete = findViewById<MaterialButton>(R.id.btnDeleteRecipe)

        // --- Spinner setup ---
        val categories = listOf("Breakfast","Brunch","Lunch","Dinner","Desserts","Other")
        spinner.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, categories
        )

        // --- Load recipe from DB ---
        val dao = AppDatabase.getInstance(this).recipeDao()
        recipeId = intent.getLongExtra("id", -1L)
        if (recipeId != -1L) {
            lifecycleScope.launch {
                dao.getById(recipeId)?.let { r ->
                    etName.setText(r.name)
                    spinner.setSelection(categories.indexOf(r.category))
                    etIngr.setText(r.ingredients)
                    etInstr.setText(r.instructions)
                }
            }
        }

        // --- Cancel ---
        btnCancel.setOnClickListener { finish() }

        // --- Save changes ---
        btnSave.setOnClickListener {
            val updated = Recipe(
                id = recipeId,
                name = etName.text.toString().trim(),
                category = spinner.selectedItem as String,
                ingredients = etIngr.text.toString().trim(),
                instructions = etInstr.text.toString().trim()
            )
            lifecycleScope.launch {
                dao.update(updated)
                finish()
            }
        }

        // --- Delete ---
        btnDelete.setOnClickListener {
            // Show a simple confirmation dialog
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe? This cannot be undone.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    lifecycleScope.launch {
                        dao.delete(Recipe(id = recipeId, name="",category="",ingredients="",instructions=""))
                        finish()
                    }
                }
                .show()
        }
    }
}