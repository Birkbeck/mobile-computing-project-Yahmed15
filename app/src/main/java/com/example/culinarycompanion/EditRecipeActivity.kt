package com.example.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.databinding.ActivityEditRecipeBinding

class EditRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.toolbarEdit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarEdit.setNavigationOnClickListener { finish() }

        // TODO: prefill binding.etName, binding.etIngredients, binding.etInstructions

        binding.btnSaveChanges.setOnClickListener {
            // TODO: update in Room, finish()
        }
        binding.btnDeleteRecipe.setOnClickListener {
            // TODO: show delete confirmation dialog
        }
    }
}