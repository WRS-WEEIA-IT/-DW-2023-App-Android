package com.app.dw2023.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.dw2023.Global.*
import com.app.dw2023.R
import com.app.dw2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    var isActivityOpenedAfterScanner = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blackNavBar)

        mainBinding.bottomNavView.background = null

        mainBinding.fab.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
            finish()
        }

        isActivityOpenedAfterScanner = intent.getBooleanExtra(PREF_ACTIVITY_AFTER_SCANNER, false)

        getSavedData()

        val navController = findNavController(R.id.fragmentContainerView)
        mainBinding.bottomNavView.setupWithNavController(navController)

        openAfterScanner()
    }

    override fun onPause() {
        saveData()
        super.onPause()
    }

    private fun saveData() {
        sharedPreferences.edit().putStringSet(PREF_QR_CODES, AppData.loadedQrCodes).apply()
        sharedPreferences.edit().putInt(PREF_GAINED_POINTS, AppData.gainedPoints).apply()
        sharedPreferences.edit().putInt(PREF_LAST_SELECTED_FRAGMENT_INDEX, AppData.lastSelectedIndex).apply()
        sharedPreferences.edit().putBoolean(PREF_DIRTY_POINTS, AppData.successfullySavedPoints).apply()
    }

    private fun getSavedData() {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        AppData.lastSelectedIndex = sharedPreferences.getInt(PREF_LAST_SELECTED_FRAGMENT_INDEX, 0)
        AppData.loadedQrCodes = sharedPreferences.getStringSet(PREF_QR_CODES, setOf())!!.toMutableSet()
        AppData.gainedPoints = sharedPreferences.getInt(PREF_GAINED_POINTS, 0)
    }

    private fun openAfterScanner() {
        if (isActivityOpenedAfterScanner) {
            mainBinding.bottomNavView.selectedItemId = mainBinding.bottomNavView.menu.getItem(AppData.lastSelectedIndex).itemId
        }
    }
}