package com.example.culinarycompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        // Toolbar & Logo
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFilter)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        findViewById<ImageView>(R.id.imgLogoFilter).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)
                .apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP })
        }

        // CheckBoxes
        val cbBreakfast = findViewById<MaterialCheckBox>(R.id.cbBreakfast)
        val cbBrunch    = findViewById<MaterialCheckBox>(R.id.cbBrunch)
        val cbLunch     = findViewById<MaterialCheckBox>(R.id.cbLunch)
        val cbDinner    = findViewById<MaterialCheckBox>(R.id.cbDinner)
        val cbDessert   = findViewById<MaterialCheckBox>(R.id.cbDessert)
        val cbOther     = findViewById<MaterialCheckBox>(R.id.cbOther)

        // Buttons
        findViewById<MaterialButton>(R.id.btnClearFilter).setOnClickListener {
            listOf(cbBreakfast, cbBrunch, cbLunch, cbDinner, cbDessert, cbOther)
                .forEach { it.isChecked = false }
        }

        findViewById<MaterialButton>(R.id.btnApplyFilter).setOnClickListener {
            // Collect selected categories
            val selected = mutableListOf<String>()
            if (cbBreakfast.isChecked) selected += "Breakfast"
            if (cbBrunch   .isChecked) selected += "Brunch"
            if (cbLunch    .isChecked) selected += "Lunch"
            if (cbDinner   .isChecked) selected += "Dinner"
            if (cbDessert  .isChecked) selected += "Desserts"
            if (cbOther    .isChecked) selected += "Other"

            // Return selection to Dashboard
            Intent().apply {
                putStringArrayListExtra("filter_list", ArrayList(selected))
            }.also {
                setResult(RESULT_OK, it)
                finish()
            }
        }
    }
}