package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.FragmentAddRecipeBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class AddRecipeFragment : Fragment() {
    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.Factory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Back arrow
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Populate category spinner from array resource
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            binding.spinnerCategory.adapter = adapter
        }

        binding.saveButton.setOnClickListener {
            val name        = binding.etName.text.toString().trim()
            val ingredients = binding.etIngredients.text.toString().trim()
            val instructions= binding.etInstructions.text.toString().trim()
            val category    = binding.spinnerCategory.selectedItem.toString()

            if (name.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                lifecycleScope.launch {
                    viewModel.insert(
                        Recipe(
                            name = name,
                            ingredients = ingredients,
                            instructions = instructions,
                            category = category
                        )
                    )
                    findNavController().navigateUp()
                }
            } else {
                // TODO: show validation error (e.g. Toast)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}