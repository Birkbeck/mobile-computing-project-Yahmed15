package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentProfileBinding
import com.example.culinarycompanion.ui.ProfileFragmentDirections
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import com.example.culinarycompanion.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userVM: UserViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }
    private val recipeVM: RecipeViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    private lateinit var favoritesAdapter: FavoriteRecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentProfileBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar back-arrow → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }
        // explicit back button → Dashboard
        binding.btnBackToDashboard.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // set up favorites carousel
        favoritesAdapter = FavoriteRecipesAdapter { recipe ->
            val action = ProfileFragmentDirections
                .actionProfileFragmentToRecipeDetailFragment(recipe.id)
            findNavController().navigate(action)
        }
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = favoritesAdapter
        }

        // observe and display user info
        userVM.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvFullName.text = user.fullName
                binding.tvUsername.text = user.username
                binding.tvEmail   .text = user.email
            } else {
                // no user → kick back to login
                findNavController().popBackStack(R.id.loginFragment, false)
            }
        }

        // show only favorites
        recipeVM.allRecipes.observe(viewLifecycleOwner) { list ->
            favoritesAdapter.submitList(list.filter { it.isFavorite })
        }

        // logout → clear VM + go to login
        binding.btnLogout.setOnClickListener {
            userVM.logout()
            findNavController().popBackStack(R.id.loginFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}