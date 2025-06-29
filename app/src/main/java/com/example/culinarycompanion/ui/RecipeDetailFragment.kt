package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.FragmentRecipeDetailBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    // pull in the ViewModel
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.Factory(requireContext())
    }

    // will hold the recipeId passed via nav
    private var recipeId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeId = arguments?.getLong("recipeId", -1L) ?: -1L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // back arrow
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // observe the recipe by ID
        viewModel.getRecipe(recipeId).observe(viewLifecycleOwner) { recipe: Recipe? ->
            if (recipe != null) {
                binding.recipeIdText.text     = getString(R.string.recipe_detail) + " #${recipe.id}"
                binding.titleText.text        = recipe.name
                binding.ingredientsText.text  = recipe.ingredients
                binding.instructionsText.text = recipe.instructions

                // edit → EditRecipeFragment
                binding.editButton.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_detail_to_editRecipe,
                        bundleOf("recipeId" to recipe.id)
                    )
                }
                // delete → pop back after removing
                binding.deleteButton.setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.delete(recipe)
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}