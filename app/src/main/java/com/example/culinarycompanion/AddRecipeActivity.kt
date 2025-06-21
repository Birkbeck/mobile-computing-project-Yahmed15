package com.example.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAdd.setNavigationOnClickListener { finish() }

        // save button
        binding.btnSaveRecipe.setOnClickListener {
            // TODO: read binding.etName, binding.etIngredients, binding.etInstructions
            // TODO: save to Room and finish()
        }
    }
}