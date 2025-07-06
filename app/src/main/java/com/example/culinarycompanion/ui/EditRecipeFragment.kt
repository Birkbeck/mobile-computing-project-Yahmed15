package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.FragmentEditRecipeBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class EditRecipeFragment : Fragment() {
    private var _binding: FragmentEditRecipeBinding? = null
    private val binding get() = _binding!!

    private val args: EditRecipeFragmentArgs by navArgs()
    private lateinit var recipe: Recipe

    private val recipeVM: RecipeViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentEditRecipeBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = args.recipeId

        recipeVM.getRecipeById(id).observe(viewLifecycleOwner) { r ->
            recipe = r
            binding.etName.setText(r.name)
            binding.etIngredients.setText(r.ingredients)
            binding.etInstructions.setText(r.instructions)

            val adapter = binding.spinnerCategory.adapter as ArrayAdapter<String>
            binding.spinnerCategory.setSelection(adapter.getPosition(r.category))
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                recipeVM.delete(recipe)
                findNavController().navigateUp()
            }
        }

        binding.btnSave.setOnClickListener {
            val updated = recipe.copy(
                name = binding.etName.text.toString().trim(),
                ingredients = binding.etIngredients.text.toString().trim(),
                instructions = binding.etInstructions.text.toString().trim(),
                category = binding.spinnerCategory.selectedItem.toString()
            )
            lifecycleScope.launch {
                recipeVM.update(updated)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}