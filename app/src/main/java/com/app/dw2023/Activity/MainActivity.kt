package com.app.dw2023.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.dw2023.R
import com.app.dw2023.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.hide()

        window.navigationBarColor = ContextCompat.getColor(this, R.color.blackNavBar)

        mainBinding.bottomNavView.background = null

        val navController = findNavController(R.id.fragmentContainerView)
        mainBinding.bottomNavView.setupWithNavController(navController)
    }
}