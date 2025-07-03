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
import com.example.culinarycompanion.databinding.FragmentAddRecipeBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class AddRecipeFragment : Fragment() {
    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipeVM: RecipeViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar back-arrow → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Cancel button → Dashboard
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Save button → insert then Dashboard
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if (name.isEmpty()) {
                binding.etName.error = getString(R.string.required)
                return@setOnClickListener
            }
            val recipe = Recipe(
                name = name,
                ingredients  = binding.etIngredients.text.toString().trim(),
                instructions = binding.etInstructions.text.toString().trim(),
                category     = binding.spinnerCategory.selectedItem.toString()
            )
            viewLifecycleOwner.lifecycleScope.launch {
                recipeVM.insert(recipe)
                findNavController().popBackStack(R.id.dashboardFragment, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}