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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar Back → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToDashboardFragment()
            )
        }
        // Explicit Back → Dashboard
        binding.btnBackToDashboard.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToDashboardFragment()
            )
        }

        // Setup favorites carousel
        favoritesAdapter = FavoriteRecipesAdapter { recipe ->
            val action = ProfileFragmentDirections
                .actionProfileFragmentToRecipeDetailFragment(recipe.id)
            findNavController().navigate(action)
        }
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            adapter = favoritesAdapter
        }

        // Observe & display user info
        userVM.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvFullName.text = user.fullName
                binding.tvUsername.text = user.username
                binding.tvEmail.text = user.email
            } else {
                // not logged in → go to Login
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                )
            }
        }

        // Show only favorites
        recipeVM.allRecipes.observe(viewLifecycleOwner) { list ->
            favoritesAdapter.submitList(list.filter { it.isFavorite })
        }

        // Logout → clears user & nav to Login
        binding.btnLogout.setOnClickListener {
            userVM.logout()
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}