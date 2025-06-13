package com.example.culinarycompanion

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class EditRecipeActivity : AppCompatActivity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.activity_edit_recipe)

        val dao = AppDatabase.getInstance(this).recipeDao()
        val id  = intent.getIntExtra("id", -1)

        // Views
        val nameET    = findViewById<EditText>(R.id.etEditRecipeName)
        val spinner   = findViewById<Spinner>(R.id.spinnerEditCategory)
        val ingET     = findViewById<EditText>(R.id.etEditIngredients)
        val instrET   = findViewById<EditText>(R.id.etEditInstructions)
        val saveBtn   = findViewById<Button>(R.id.btnSaveChanges)
        val cancelBtn = findViewById<Button>(R.id.btnCancelEdit)
        val delBtn    = findViewById<Button>(R.id.btnDeleteRecipe)

        // Spinner data
        val cats = listOf("Breakfast","Brunch","Lunch","Dinner","Desserts","Other")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cats)

        // Load and prefill
        dao.getAll().observe(this) { list ->
            list.find { it.id == id }?.let { r ->
                nameET.setText(r.name)
                spinner.setSelection(cats.indexOf(r.category))
                ingET.setText(r.ingredients)
                instrET.setText(r.instructions)

                saveBtn.setOnClickListener {
                    val updated = r.copy(
                        name = nameET.text.toString(),
                        category = spinner.selectedItem as String,
                        ingredients = ingET.text.toString(),
                        instructions = instrET.text.toString()
                    )
                    lifecycleScope.launch { dao.update(updated) }
                    finish()
                }
                cancelBtn.setOnClickListener { finish() }
                delBtn.setOnClickListener {
                    lifecycleScope.launch { dao.delete(r) }
                    finish()
                }
            }
        }
    }
}