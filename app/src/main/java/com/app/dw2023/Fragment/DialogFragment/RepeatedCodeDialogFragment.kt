package com.app.dw2023.Fragment.DialogFragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.app.dw2023.Activity.ScannerActivity
import com.app.dw2023.R

class RepeatedCodeDialogFragment : DialogFragment() {

    private lateinit var repeatedButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_repeated_code_dialog, container, false)

        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        repeatedButton = view.findViewById(R.id.repeatedButton)
        repeatedButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        (activity as ScannerActivity).returnToMainActivity()
        requireActivity().finish()
        super.onDismiss(dialog)
    }
}