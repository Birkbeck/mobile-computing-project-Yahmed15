package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // CLEAR: uncheck all
        binding.btnClearFilter.setOnClickListener {
            listOf(
                binding.checkBoxBreakfast,
                binding.checkBoxBrunch,
                binding.checkBoxLunch,
                binding.checkBoxDinner,
                binding.checkBoxDessert,
                binding.checkBoxOther
            ).forEach { it.isChecked = false }
        }

        // APPLY: collect selected and send back to Dashboard (via SavedStateHandle)
        binding.btnApplyFilter.setOnClickListener {
            val selected = mutableListOf<String>()
            if (binding.checkBoxBreakfast.isChecked) selected += getString(R.string.category_breakfast)
            if (binding.checkBoxBrunch.isChecked)    selected += getString(R.string.category_brunch)
            if (binding.checkBoxLunch.isChecked)     selected += getString(R.string.category_lunch)
            if (binding.checkBoxDinner.isChecked)    selected += getString(R.string.category_dinner)
            if (binding.checkBoxDessert.isChecked)   selected += getString(R.string.category_dessert)
            if (binding.checkBoxOther.isChecked)     selected += getString(R.string.category_other)

            // pass the list back
            findNavController()
                .previousBackStackEntry
                ?.savedStateHandle
                ?.set("filterCategories", selected)

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}