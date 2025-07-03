package com.example.culinarycompanion.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.R
import com.example.culinarycompanion.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Back arrow → Dashboard
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, false)
        }

        // Show version from PackageInfo
        val versionName = try {
            val pi = requireContext().packageManager
                .getPackageInfo(requireContext().packageName, 0)
            pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "?"
        }
        binding.tvAppVersion.text = getString(R.string.app_version_format, versionName)

        // Notifications toggle stub
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Snackbar.make(
                binding.root,
                if (isChecked)
                    R.string.notifications_enabled
                else
                    R.string.notifications_disabled,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        // Navigation cards
        binding.cardHelpFaq.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_faqFragment)
        }
        binding.cardContact.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_contactFragment)
        }
        binding.cardPrivacyPolicy.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_privacyPolicyFragment)
        }
        binding.cardAbout.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}