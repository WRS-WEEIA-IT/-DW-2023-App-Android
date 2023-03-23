package com.app.dw2023.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.app.dw2023.Global.AppData
import com.app.dw2023.Global.PREF_NAME
import com.app.dw2023.Global.SETTINGS_FRAGMENT_INDEX
import com.app.dw2023.R

class InfoFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var pointsButton: AppCompatButton
    private lateinit var infoAppID: TextView
    private lateinit var contactUsButton: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        pointsButton = view.findViewById(R.id.infoPointsButton)
        infoAppID = view.findViewById(R.id.infoAppID)
        contactUsButton = view.findViewById(R.id.infoContactUsButton)

        val points = "You have ${AppData.gainedPoints} points!"
        pointsButton.text = points
        pointsButton.isClickable = false

        if (AppData.userID != 0) {
            val appID = "App ID: ${AppData.userID}"
            infoAppID.text = appID
        }

        contactUsButton.setOnClickListener {
            sendEmail()
        }

        AppData.lastSelectedIndex = SETTINGS_FRAGMENT_INDEX

//        debugClearData()

        return view
    }

    private fun debugClearData() {
        AppData.loadedQrCodes = mutableSetOf()
        AppData.gainedPoints = 0
        AppData.tasksList = arrayListOf()

        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendEmail() {
        val emailAddress = arrayOf("dzien.weeia@samorzad.p.lodz.pl")
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress)

        if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Choose your e-mail app"))
        }
    }

}