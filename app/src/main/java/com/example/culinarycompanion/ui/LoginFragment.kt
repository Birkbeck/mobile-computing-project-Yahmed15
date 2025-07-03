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
import com.example.culinarycompanion.databinding.FragmentLoginBinding
import com.example.culinarycompanion.viewmodel.UserViewModel
import com.example.culinarycompanion.viewmodel.UserViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userVM: UserViewModel by viewModels {
        UserViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar back-arrow -> Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Cancel -> Dashboard
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Forgot password feedback
        binding.tvForgot.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.reset_link_sent),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    R.string.fill_both_fields,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            userVM.login(username, password).observe(viewLifecycleOwner) { success ->
                if (success) {
                    findNavController().navigate(
                        LoginFragmentDirections
                            .actionLoginFragmentToProfileFragment()
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.invalid_credentials,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}