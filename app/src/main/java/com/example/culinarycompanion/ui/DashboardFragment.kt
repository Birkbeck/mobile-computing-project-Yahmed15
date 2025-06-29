package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentDashboardBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Use activityViewModels so AddRecipe updates immediately appear here
    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModel.Factory(requireContext())
    }

    private lateinit var fullAdapter: RecipeListAdapter
    private lateinit var trendingAdapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDashboardBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 1) Trending carousel
        trendingAdapter = RecipeListAdapter { recipe ->
            findNavController().navigate(
                R.id.action_dashboard_to_detail,
                bundleOf("recipeId" to recipe.id)
            )
        }
        binding.rvTrending.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        // 2) Full vertical list
        fullAdapter = RecipeListAdapter { recipe ->
            findNavController().navigate(
                R.id.action_dashboard_to_detail,
                bundleOf("recipeId" to recipe.id)
            )
        }
        binding.rvRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fullAdapter
        }

        // 3) Observe master list
        viewModel.allRecipes.observe(viewLifecycleOwner) { list ->
            fullAdapter.submitList(list)
            trendingAdapter.submitList(list.takeLast(5).reversed())
        }

        // 4) Add & Filter buttons
        binding.btnNewRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addRecipe)
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_filter)
        }

        // 5) Chip‐based category filter
        binding.chipGroupCategories.setOnCheckedChangeListener { _, checkedId ->
            val live = when (checkedId) {
                R.id.chipBreakfast -> viewModel.filterBy("Breakfast")
                R.id.chipBrunch   -> viewModel.filterBy("Brunch")
                R.id.chipLunch    -> viewModel.filterBy("Lunch")
                R.id.chipDinner   -> viewModel.filterBy("Dinner")
                R.id.chipDessert  -> viewModel.filterBy("Dessert")
                R.id.chipOther    -> viewModel.filterBy("Other")
                else              -> viewModel.allRecipes
            }
            live.observe(viewLifecycleOwner) { filtered ->
                fullAdapter.submitList(filtered)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}