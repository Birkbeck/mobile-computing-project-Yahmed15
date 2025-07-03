package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.FragmentRecipeDetailBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    // ViewModel via AndroidViewModelFactory
    private val recipeVM: RecipeViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }
    private lateinit var recipe: Recipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar back → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Back button below → Dashboard
        binding.btnBackToDashboard.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Load recipe
        val id = arguments?.getLong("recipeId") ?: return
        recipeVM.getRecipe(id).observe(viewLifecycleOwner) { r ->
            recipe = r
            binding.titleText.text =
                r.name
            binding.recipeIdText.text =
                getString(R.string.recipe_id_format, r.id)
            binding.ingredientsText.text = r.ingredients
            binding.instructionsText.text = r.instructions

            // Favorite toggle
            binding.btnFavorite.apply {
                setIconResource(
                    if (r.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_border
                )
                setOnClickListener {
                    r.isFavorite = !r.isFavorite
                    lifecycleScope.launch {
                        recipeVM.setFavorite(r.id, r.isFavorite)
                        setIconResource(
                            if (r.isFavorite) R.drawable.ic_favorite_filled
                            else R.drawable.ic_favorite_border
                        )
                    }
                }
            }
        }

        // Edit
        binding.editButton.setOnClickListener {
            val action = RecipeDetailFragmentDirections
                .actionRecipeDetailFragmentToEditRecipeFragment(recipe.id)
            findNavController().navigate(action)
        }

        // Delete → and back to dashboard
        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch {
                recipeVM.delete(recipe)
                findNavController().popBackStack(R.id.dashboardFragment, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}