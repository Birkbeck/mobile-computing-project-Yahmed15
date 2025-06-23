package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.culinarycompanion.databinding.FragmentEditRecipeBinding
import com.example.culinarycompanion.model.Recipe
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class EditRecipeFragment : Fragment() {
    private val args: EditRecipeFragmentArgs by navArgs()
    private var _binding: FragmentEditRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getRecipeById(args.recipeId).observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                binding.etTitle.setText(it.title)
                binding.etDescription.setText(it.description)

                binding.btnUpdate.setOnClickListener {
                    val updated = Recipe(it.id, binding.etTitle.text.toString(), binding.etDescription.text.toString())
                    viewModel.update(updated)
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}