package com.example.culinarycompanion

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddRecipeActivity : AppCompatActivity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.activity_add_recipe)

        val dao = AppDatabase.getInstance(this).recipeDao()

        val nameET = findViewById<EditText>(R.id.etRecipeName)
        val spinner = findViewById<Spinner>(R.id.spinnerCategory)
        val ingET = findViewById<EditText>(R.id.etIngredients)
        val instrET = findViewById<EditText>(R.id.etInstructions)

        // Populate spinner
        val cats = listOf("Breakfast","Brunch","Lunch","Dinner","Desserts","Other")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cats)

        findViewById<Button>(R.id.btnSaveRecipe).setOnClickListener {
            val r = Recipe(
                name = nameET.text.toString(),
                category = spinner.selectedItem as String,
                ingredients = ingET.text.toString(),
                instructions = instrET.text.toString()
            )
            lifecycleScope.launch { dao.insert(r) }
            finish()
        }
        // Cancel: just close
        findViewById<Button>(R.id.btnCancelAdd)
            .setOnClickListener { finish() }
    }
}