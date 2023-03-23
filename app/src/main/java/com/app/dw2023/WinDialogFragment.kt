package com.app.dw2023

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment

class WinDialogFragment : DialogFragment() {

    private lateinit var winOKButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_win_dialog, container, false)

        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        winOKButton = view.findViewById(R.id.winOKButton)
        winOKButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return view
    }
}