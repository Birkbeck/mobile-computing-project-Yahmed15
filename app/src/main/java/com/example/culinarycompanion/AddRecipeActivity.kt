package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class AddRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        // --- App Database DAO ---
        val dao = AppDatabase.getInstance(this).recipeDao()

        // --- Toolbar & Logo ---
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarAdd)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Back arrow
        toolbar.setNavigationOnClickListener { finish() }
        // Logo click → Dashboard
        findViewById<ImageView>(R.id.imgLogo).setOnClickListener {
            Intent(this, DashboardActivity::class.java).apply {
                // Clear back stack so Dashboard becomes root
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }.also(::startActivity)
        }

        // --- Form fields ---
        val etName    = findViewById<EditText>(R.id.etRecipeName)
        val spinner   = findViewById<Spinner>(R.id.spinnerCategory)
        val etIngr    = findViewById<EditText>(R.id.etIngredients)
        val etInstr   = findViewById<EditText>(R.id.etInstructions)

        // Populate category spinner
        val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        // --- Action buttons ---
        val btnCancel = findViewById<MaterialButton>(R.id.btnCancelAdd)
        val btnSave   = findViewById<MaterialButton>(R.id.btnSaveRecipe)

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val cat  = spinner.selectedItem as String
            val ing  = etIngr.text.toString().trim()
            val instr= etInstr.text.toString().trim()

            if (name.isEmpty() || ing.isEmpty() || instr.isEmpty()) {
                // Simple validation
                etName.error = if (name.isEmpty()) "Required" else null
                etIngr.error = if (ing.isEmpty()) "Required" else null
                etInstr.error= if (instr.isEmpty()) "Required" else null
            } else {
                // Insert into Room on background thread
                lifecycleScope.launch {
                    dao.insert(
                        Recipe(
                            name = name,
                            category = cat,
                            ingredients = ing,
                            instructions = instr
                        )
                    )
                    // After saving, return home
                    Intent(this@AddRecipeActivity, DashboardActivity::class.java)
                        .apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        }
                        .also(::startActivity)
                    finish()
                }
            }
        }
    }
}