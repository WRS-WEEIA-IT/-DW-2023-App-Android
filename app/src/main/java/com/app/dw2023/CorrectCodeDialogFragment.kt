package com.app.dw2023

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.app.dw2023.Activity.ScannerActivity
import com.app.dw2023.Global.ImagesMap

class CorrectCodeDialogFragment : DialogFragment() {

    private lateinit var correctOkButton: AppCompatButton

    lateinit var correctTasksNumberTextView: TextView
    lateinit var correctTasksTitleTextView: TextView
    lateinit var correctTasksTipsTextView: TextView
    lateinit var correctTasksCardPointsButton: AppCompatButton
    lateinit var correctTasksQRCodeImageView: ImageView
    lateinit var correctTasksQRCodeTextView: TextView
    lateinit var correctTasksCardImageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_correct_code_dialog, container, false)

        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        correctTasksCardPointsButton = view.findViewById(R.id.correctTasksCardPointsButton)
        correctTasksTipsTextView = view.findViewById(R.id.correctTasksTipsTextView)
        correctTasksCardImageView = view.findViewById(R.id.correctTasksCardImageView)
        correctTasksTitleTextView = view.findViewById(R.id.correctTasksTitleTextView)
        correctTasksNumberTextView = view.findViewById(R.id.correctTasksNumberTextView)

        setupCorrectTaskPopUp()

        correctOkButton = view.findViewById(R.id.correctButton)
        correctOkButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        (activity as ScannerActivity).returnToMainActivity()
        requireActivity().finish()
        super.onDismiss(dialog)
    }

    private fun setupCorrectTaskPopUp() {
        val points = "${arguments?.getInt("completedTaskPointsBundle").toString()} points"
        val imageSource = arguments?.getString("completedTaskBackgroundImageBungle")
        val taskNumberText = "Task ${arguments?.getInt("completedTaskTaskNumberBundle")}"
        val drawableId = ImagesMap.imagesMap[imageSource] ?: R.drawable.event_card_background

        correctTasksCardPointsButton.text = points
        correctTasksTipsTextView.text = arguments?.getString("completedTaskTipsBundle")
        correctTasksCardImageView.setImageResource(drawableId)
        correctTasksTitleTextView.text = arguments?.getString("completedTaskTitleBundle")
        correctTasksNumberTextView.text = taskNumberText
    }
}