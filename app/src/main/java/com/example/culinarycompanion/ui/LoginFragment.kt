package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentLoginBinding
import com.example.culinarycompanion.viewmodel.UserViewModel
import com.example.culinarycompanion.viewmodel.UserViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // 🚩 Share the same VM as ProfileFragment
    private val userVM: UserViewModel by activityViewModels {
        UserViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Toolbar back-arrow → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Cancel button → Dashboard
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Forgot-password link
        binding.tvForgot.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.reset_link_sent),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text?.toString()?.trim().orEmpty()
            val password = binding.etPassword.text?.toString()?.trim().orEmpty()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    R.string.fill_both_fields,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            userVM.login(username, password)
                .observe(viewLifecycleOwner) { success ->
                    if (success) {
                        // Safe-args navigation to ProfileFragment
                        val action = LoginFragmentDirections
                            .actionLoginFragmentToProfileFragment()
                        findNavController().navigate(action)
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