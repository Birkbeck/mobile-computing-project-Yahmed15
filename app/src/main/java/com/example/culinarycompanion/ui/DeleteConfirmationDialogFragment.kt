package com.example.culinarycompanion.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class DeleteConfirmationDialogFragment : DialogFragment() {

    private val viewModel: RecipeViewModel by viewModels({
        requireParentFragment()
    }) {
        RecipeViewModel.Factory(requireContext())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val recipeId = requireArguments().getLong("recipeId")
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_this_recipe)
            .setMessage(R.string.delete_confirmation_text)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.delete) { _, _ ->
                // Must call suspend function inside coroutine
                lifecycleScope.launch {
                    // First load the Recipe entity if you need it, or delete by id:
                    viewModel.getRecipe(recipeId).value?.let { recipe ->
                        viewModel.delete(recipe)
                    }
                    dismiss()
                    findNavController().navigateUp()
                }
            }
            .create()
    }
}