package com.example.culinarycompanion.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.culinarycompanion.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFilterBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // clear all checkboxes
        binding.btnClearFilter.setOnClickListener {
            binding.checkBoxBreakfast.isChecked = false
            binding.checkBoxBrunch.isChecked    = false
            // … also clear lunch, dinner, dessert, other …
        }

        // apply just returns to dashboard; real filtering would need to talk back via FragmentResult
        binding.btnApplyFilter.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}