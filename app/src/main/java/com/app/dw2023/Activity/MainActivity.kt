package com.app.dw2023.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.dw2023.R
import com.app.dw2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    companion object {
        var loadedQrCodes = mutableSetOf<String>()
        var gainedPoints = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blackNavBar)

        mainBinding.bottomNavView.background = null

        sharedPreferences = getSharedPreferences("progress", Context.MODE_PRIVATE)
        loadedQrCodes = sharedPreferences.getStringSet("qr_codes_key", setOf())!!.toMutableSet()
        Log.d("verySecretMessage", "read codes from sharedPreferences")

        mainBinding.fab.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }

        val navController = findNavController(R.id.fragmentContainerView)
        mainBinding.bottomNavView.setupWithNavController(navController)
    }

    override fun onPause() {
        sharedPreferences.edit().putStringSet("qr_codes_key", loadedQrCodes).apply()
        Log.d("verySecretMessage", "Main onPause, saved codes")
        super.onPause()
    }
}