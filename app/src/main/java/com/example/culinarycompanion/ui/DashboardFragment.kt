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

    private val viewModel: RecipeViewModel by activityViewModels {
        RecipeViewModel.Factory(requireContext())
    }

    private lateinit var adapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // RecyclerView setup
        adapter = RecipeListAdapter { r ->
            findNavController().navigate(
                R.id.action_dashboard_to_detail,
                bundleOf("recipeId" to r.id)
            )
        }
        binding.rvRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DashboardFragment.adapter
        }

        // Observe all recipes
        viewModel.allRecipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // + button
        binding.btnNewRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addRecipe)
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_filter)
        }

        // Category chips
        binding.chipGroupCategories.setOnCheckedChangeListener { _, id ->
            val live = when (id) {
                R.id.chipBreakfast -> viewModel.filterBy("Breakfast")
                R.id.chipBrunch    -> viewModel.filterBy("Brunch")
                R.id.chipLunch     -> viewModel.filterBy("Lunch")
                R.id.chipDinner    -> viewModel.filterBy("Dinner")
                R.id.chipDessert   -> viewModel.filterBy("Dessert")
                R.id.chipOther     -> viewModel.filterBy("Other")
                else               -> viewModel.allRecipes
            }
            live.observe(viewLifecycleOwner) { filtered ->
                adapter.submitList(filtered)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}