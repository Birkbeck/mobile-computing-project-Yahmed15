package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentDashboardBinding
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }
    private lateinit var adapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentDashboardBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = RecipeListAdapter(
            onClick = { r ->
                val action = DashboardFragmentDirections
                    .actionDashboardFragmentToRecipeDetailFragment(r.id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { r ->
                lifecycleScope.launch {
                    viewModel.setFavorite(r.id, !r.isFavorite)
                }
            }
        )

        binding.rvRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DashboardFragment.adapter
        }

        viewModel.allRecipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnNewRecipe.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToAddRecipeFragment()
            )
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToFilterFragment()
            )
        }
        binding.chipGroupCategories.setOnCheckedChangeListener { group, checkedId ->
            val source = if (checkedId == View.NO_ID) {
                viewModel.allRecipes
            } else {
                val chip = group.findViewById<com.google.android.material.chip.Chip>(checkedId)
                viewModel.filterBy(chip.text.toString())
            }
            source.observe(viewLifecycleOwner) { adapter.submitList(it) }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dash_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}