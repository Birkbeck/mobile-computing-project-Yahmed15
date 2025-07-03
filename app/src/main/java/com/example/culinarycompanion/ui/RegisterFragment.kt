package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentRegisterBinding
import com.example.culinarycompanion.viewmodel.UserViewModel
import com.example.culinarycompanion.viewmodel.UserViewModelFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val userVM: UserViewModel by viewModels {
        UserViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar back arrow → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Cancel button → Dashboard
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val email    = binding.etEmail   .text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val agreed   = binding.cbAgree.isChecked

            when {
                fullName.isEmpty() -> binding.etFullName.error = getString(R.string.required)
                email.isEmpty()    -> binding.etEmail   .error = getString(R.string.required)
                username.isEmpty() -> binding.etUsername.error = getString(R.string.required)
                password.isEmpty() -> binding.etPassword.error = getString(R.string.required)
                !agreed            -> Toast.makeText(
                    requireContext(),
                    R.string.must_agree_terms,
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    userVM.register(username, fullName, email, password)
                        .observe(viewLifecycleOwner) { newId ->
                            if (newId > 0) {
                                // Safe-args navigation to Profile
                                findNavController().navigate(
                                    R.id.actionRegisterFragmentToProfileFragment
                                )
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.registration_failed,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}