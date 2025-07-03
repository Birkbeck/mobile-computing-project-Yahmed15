package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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

    // keep a copy of the full list for filtering
    private var masterList: List<Recipe> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentDashboardBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ① Adapter with onClick → detail, onFavoriteClick → toggle
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

        // ② Observe full list, store masterList, then filter
        viewModel.allRecipes.observe(viewLifecycleOwner) { list ->
            masterList = list
            filterAndDisplay()
        }

        // ③ Populate chips from string-array (res/values/arrays.xml)
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

        // ④ Trigger search when IME action is “Search”
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                filterAndDisplay()
                true
            } else false
        }

        // ⑤ Nav buttons
        binding.btnNewRecipe.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToAddRecipeFragment()
            )
        }
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToProfileFragment()
            )
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToLoginFragment()
            )
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToRegisterFragment()
            )
        }
        binding.fabSettings.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToSettingsFragment()
            )
        }
    }

    /** Filter masterList by selected chip + search text, then submit to adapter */
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
            it.name.contains(query, ignoreCase = true)
        }

        adapter.submitList(finalList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}