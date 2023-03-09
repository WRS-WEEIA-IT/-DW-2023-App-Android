package com.app.dw2023.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dw2023.Global.AppData
import com.app.dw2023.Global.PREF_NAME
import com.app.dw2023.Global.SETTINGS_FRAGMENT_INDEX
import com.app.dw2023.R

class InfoFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        AppData.lastSelectedIndex = SETTINGS_FRAGMENT_INDEX

        debugClearData()

        return view
    }

    private fun debugClearData() {
        AppData.loadedQrCodes = mutableSetOf()
        AppData.gainedPoints = 0
        AppData.tasksList = arrayListOf()

        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

}