package com.app.dw2023.Fragment.DialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.app.dw2023.R

class LoseDialogFragment : DialogFragment() {

    private lateinit var loseOKButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lose_dialog, container, false)

        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        loseOKButton = view.findViewById(R.id.loseOKButton)
        loseOKButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return view
    }

}