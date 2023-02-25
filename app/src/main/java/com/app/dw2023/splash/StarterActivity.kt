package com.app.dw2023.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.app.dw2023.R
import com.app.dw2023.activity.MainActivity

class StarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.blackNavBar)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@StarterActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}