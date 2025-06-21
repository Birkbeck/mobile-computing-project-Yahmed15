package com.example.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.toolbarFilter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarFilter.setNavigationOnClickListener { finish() }

        binding.btnApplyFilter.setOnClickListener {
            // TODO: gather binding.checkboxBreakfast.isChecked, etc.
            // TODO: return result via setResult() and finish()
        }
        binding.btnClearFilter.setOnClickListener {
            binding.checkboxBreakfast.isChecked = false
            binding.checkboxBrunch.isChecked = false
            // … clear the rest …
        }
    }
}