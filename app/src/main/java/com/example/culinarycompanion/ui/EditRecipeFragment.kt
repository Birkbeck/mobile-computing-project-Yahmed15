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
import com.example.culinarycompanion.databinding.FragmentEditRecipeBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class EditRecipeFragment : Fragment() {
    private var _binding: FragmentEditRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.Factory(requireContext())
    }
    private var recipeId: Long = -1L
    private var currentRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong("recipeId", -1L)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up spinner options
        val categories = resources.getStringArray(R.array.category_array)
        binding.spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        // Back arrow
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Load existing recipe
        viewModel.getRecipe(recipeId).observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                currentRecipe = it
                binding.etName.setText(it.name)
                binding.etIngredients.setText(it.ingredients)
                binding.etInstructions.setText(it.instructions)
                // set spinner to match
                val pos = categories.indexOf(it.category)
                if (pos >= 0) binding.spinnerCategory.setSelection(pos)
            }
        }

        // Cancel -> just go back
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        // Save -> update and back
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val ingredients = binding.etIngredients.text.toString().trim()
            val instructions = binding.etInstructions.text.toString().trim()
            val category = binding.spinnerCategory.selectedItem as String

            if (name.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                lifecycleScope.launch {
                    currentRecipe?.let { orig ->
                        viewModel.update(
                            orig.copy(
                                name = name,
                                ingredients = ingredients,
                                instructions = instructions,
                                category = category
                            )
                        )
                    }
                    findNavController().navigateUp()
                }
            } else {
                // You could show a Toast error here
            }
        }

        // Delete -> remove and back
        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                currentRecipe?.let { viewModel.delete(it) }
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}