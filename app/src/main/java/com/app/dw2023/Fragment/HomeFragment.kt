package com.app.dw2023.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.app.dw2023.Global.AppData
import com.app.dw2023.Global.HOME_FRAGMENT_INDEX
import com.app.dw2023.R

class HomeFragment : Fragment() {

    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        AppData.lastSelectedIndex = HOME_FRAGMENT_INDEX
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        scrollView = view.findViewById(R.id.homeScrollView)
        scrollView.isVerticalScrollBarEnabled = false

        return view
    }

}