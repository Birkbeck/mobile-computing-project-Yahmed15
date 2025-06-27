// app/src/main/java/com/example/culinarycompanion/ui/RecipeDetailFragment.kt
package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private var recipeId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong("recipeId", -1L)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recipeIdText.text = "Recipe ID: $recipeId"
        binding.editButton.setOnClickListener {
            val args = Bundle().apply { putLong("recipeId", recipeId) }
            findNavController().navigate(R.id.action_detail_to_editRecipe, args)
        }
        // TODO: load recipe from ViewModel by ID and populate title/instructions
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}