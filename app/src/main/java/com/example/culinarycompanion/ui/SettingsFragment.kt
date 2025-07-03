package com.example.culinarycompanion.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.culinarycompanion.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Inflate the preferences from XML
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}