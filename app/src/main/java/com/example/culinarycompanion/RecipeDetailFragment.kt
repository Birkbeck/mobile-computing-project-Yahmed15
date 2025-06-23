package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.culinarycompanion.databinding.FragmentRecipeDetailBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class RecipeDetailFragment : Fragment() {
    private val args: RecipeDetailFragmentArgs by navArgs()
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getRecipeById(args.recipeId).observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                binding.tvTitle.text = it.title
                binding.tvDescription.text = it.description
                binding.btnEdit.setOnClickListener {
                    val action = RecipeDetailFragmentDirections.actionDetailToEdit(recipe.id)
                    findNavController().navigate(action)
                }
                binding.btnDelete.setOnClickListener {
                    viewModel.delete(recipe)
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