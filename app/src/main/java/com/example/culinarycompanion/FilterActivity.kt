package com.example.culinarycompanion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        // Map each checkbox ID to its category string
        val cbMap = mapOf(
            R.id.cbBreakfast to "Breakfast",
            R.id.cbBrunch    to "Brunch",
            R.id.cbLunch     to "Lunch",
            R.id.cbDinner    to "Dinner",
            R.id.cbDessert   to "Dessert",
            R.id.cbOther     to "Other"
        )

        // Apply Filter button
        findViewById<Button>(R.id.btnApplyFilter).setOnClickListener {
            // Gather checked categories
            val selected = cbMap.filter { (id, _) ->
                findViewById<CheckBox>(id).isChecked
            }.values.toList()

            // Pack into result Intent
            val resultIntent = Intent().apply {
                putStringArrayListExtra("selected", ArrayList(selected))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Clear Filter button
        findViewById<Button>(R.id.btnClearFilter).setOnClickListener {
            // Return empty list
            val resultIntent = Intent().apply {
                putStringArrayListExtra("selected", ArrayList<String>())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}