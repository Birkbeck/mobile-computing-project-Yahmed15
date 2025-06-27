package com.example.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.databinding.ActivityAddRecipeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            // TODO: read binding.etName.text, etIngredients.text, etInstructions.text
            // and save your recipe…
        }
    }
}