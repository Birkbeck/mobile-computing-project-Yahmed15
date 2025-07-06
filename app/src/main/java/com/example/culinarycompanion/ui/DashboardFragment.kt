package com.example.culinarycompanion.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.culinarycompanion.R
import com.example.culinarycompanion.data.Recipe
import com.example.culinarycompanion.databinding.FragmentDashboardBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import com.google.android.material.chip.Chip

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
    }

    private lateinit var adapter: RecipeListAdapter
    private var masterList: List<Recipe> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentDashboardBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = RecipeListAdapter(
            onClick = { recipe ->
                val action = DashboardFragmentDirections
                    .actionDashboardFragmentToRecipeDetailFragment(recipe.id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { recipe ->
                viewModel.setFavorite(recipe.id, !recipe.isFavorite)
            }
        )

        binding.rvRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DashboardFragment.adapter
        }

        viewModel.allRecipes.observe(viewLifecycleOwner) { list ->
            masterList = list
            filterAndDisplay()
        }

        val categories = resources.getStringArray(R.array.category_array)
        for (cat in categories) {
            val chip = Chip(requireContext()).apply {
                text = cat
                isCheckable = true
                setOnCheckedChangeListener { _, _ ->
                    filterAndDisplay()
                }
            }
            binding.chipGroupCategories.addView(chip)
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterAndDisplay()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnNewRecipe.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddRecipeFragment()
            )
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToProfileFragment()
            )
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToLoginFragment()
            )
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToRegisterFragment()
            )
        }
        binding.fabSettings.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment()
            )
        }
    }

    private fun filterAndDisplay() {
        val checkedId = binding.chipGroupCategories.checkedChipId
        val byCategory = if (checkedId == View.NO_ID) {
            masterList
        } else {
            val chip = binding.chipGroupCategories.findViewById<Chip>(checkedId)
            masterList.filter { it.category == chip.text.toString() }
        }

        val query = binding.etSearch.text?.toString()?.trim().orEmpty()
        val finalList = if (query.isEmpty()) byCategory
        else byCategory.filter {
            it.name.contains(query, ignoreCase = true) || it.ingredients.contains(query, ignoreCase = true)
        }

        adapter.submitList(finalList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}