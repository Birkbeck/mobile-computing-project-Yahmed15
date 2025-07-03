package com.example.culinarycompanion.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.culinarycompanion.R

class DeleteConfirmationDialogFragment(
    private val onResult: (Boolean) -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_this_recipe)
            .setMessage(R.string.delete_confirmation_text)
            .setNegativeButton(R.string.cancel) { _, _ ->
                onResult(false)
                dismiss()
            }
            .setPositiveButton(R.string.delete) { _, _ ->
                onResult(true)
                dismiss()
            }
            .create()
    }
}