package com.example.culinarycompanion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.data.RecipeDatabase
import com.example.culinarycompanion.data.RecipeRepository
import com.example.culinarycompanion.databinding.FragmentDashboardBinding
import com.example.culinarycompanion.ui.RecipeListAdapter
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // 1) Build your repo + factory
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.Factory(
            RecipeRepository(
                RecipeDatabase
                    .getInstance(requireContext())
                    .recipeDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2) Hook up the RecyclerView + adapter
        binding.recyclerView.adapter = RecipeListAdapter { recipe ->
            val action = DashboardFragmentDirections
                .actionDashboardToDetail(recipe.id)
            findNavController().navigate(action)
        }

        // 3) Observe your LiveData
        viewModel.recipes.observe(viewLifecycleOwner) { list ->
            (binding.recyclerView.adapter as RecipeListAdapter)
                .submitList(list)
        }

        // 4) FAB click -> navigate to Add screen
        binding.fabAdd.setOnClickListener {
            findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardToAdd())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}