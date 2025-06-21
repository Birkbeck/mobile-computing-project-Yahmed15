package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarDetail.setNavigationOnClickListener { finish() }

        // TODO: load recipe from intent extra, populate:
        // binding.tvName, binding.tvCategory, binding.tvIngredients, binding.tvInstructions

        binding.btnDetailEdit.setOnClickListener {
            startActivity(Intent(this, EditRecipeActivity::class.java).apply {
                putExtra("RECIPE_ID", /*...*/)
            })
        }
        binding.btnDetailDelete.setOnClickListener {
            // TODO: show delete confirmation dialog
        }
    }
}