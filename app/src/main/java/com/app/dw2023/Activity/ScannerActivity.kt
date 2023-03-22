package com.app.dw2023.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.app.dw2023.Fragment.DialogFragment.CorrectCodeDialogFragment
import com.app.dw2023.Global.*
import com.app.dw2023.R
import com.app.dw2023.Fragment.DialogFragment.RepeatedCodeDialogFragment
import com.app.dw2023.Fragment.DialogFragment.WrongCodeDialogFragment
import com.app.dw2023.Model.Task
import com.budiyev.android.codescanner.*
import com.google.firebase.firestore.FirebaseFirestore

class ScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private val cameraRequestCode = 1
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var db: FirebaseFirestore

    var doneTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blackNavBar)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                cameraRequestCode
            )
        } else {
            startScan()
        }
    }

    private fun startScan() {
        val scannerView = findViewById<CodeScannerView>(R.id.scanner)

        /* credits for scanner:
        https://github.com/yuriy-budiyev/code-scanner
         */

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
//                Toast.makeText(this, it.text, Toast.LENGTH_LONG).show()
                val scanRes = it.text
                if (AppData.loadedQrCodes.add(scanRes)) {
                    if (!cleanFakeCodesFromDevice()) {
                        doneTask = AppData.tasksList.find { it.qrCode == scanRes }
                        if (doneTask == null) {
                            returnToMainActivity()
                            finish()
                        }
                        showCorrectCodeDialogFragment()
                        savePoints()
                        vibrateSuccessful()
                    } else {
                        vibrateFailure()
                        showWrongCodeDialogFragment()
                    }
                } else {
                    vibrateFailure()
                    showRepeatedCodeDialogFragment()
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScan()
        } else {
            returnToMainActivity()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    override fun onBackPressed() {
        returnToMainActivity()
        finish()
        onBackPressedDispatcher.onBackPressed()
    }

    private fun cleanFakeCodesFromDevice(): Boolean {
        AppData.validQRCodes = AppData.tasksList.mapNotNull { it.qrCode }
        return AppData.loadedQrCodes.retainAll(AppData.validQRCodes.toSet())
    }

    private fun savePoints() {
        AppData.pointsList =
            AppData.tasksList.filter { AppData.loadedQrCodes.contains(it.qrCode) }
                .map { it.points }
        val totalScore = AppData.pointsList.sum()
        if (totalScore > AppData.gainedPoints) {
            AppData.gainedPoints = totalScore
        }
        updateUserPointsInFirestore()

        sharedPreferences.edit().putInt(PREF_GAINED_POINTS, AppData.gainedPoints).apply()
        sharedPreferences.edit().putStringSet(PREF_QR_CODES, AppData.loadedQrCodes).apply()
    }

    fun returnToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(PREF_ACTIVITY_AFTER_SCANNER, true)
        AppData.afterScanner = true
        startActivity(intent)
    }

    private fun vibrateFailure() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 250, 100, 250, 100, 250), intArrayOf(0, 255, 0, 255, 0, 255), -1))
        } else {
            val pattern = longArrayOf(0, 250, 100, 250, 100, 250)
            @Suppress("DEPRECATION")
            vibrator.vibrate(pattern, -1)
        }
    }

    private fun vibrateSuccessful() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(800)
        }
    }

    private fun showWrongCodeDialogFragment() {
        val fragmentManager : FragmentManager = supportFragmentManager
        val wrongCodeDialogFragment = WrongCodeDialogFragment()

        wrongCodeDialogFragment.show(fragmentManager, "WrongCodeDialogFragment")
        wrongCodeDialogFragment.isCancelable = false
    }

    private fun showRepeatedCodeDialogFragment() {
        val fragmentManager : FragmentManager = supportFragmentManager
        val repeatedCodeDialogFragment = RepeatedCodeDialogFragment()

        repeatedCodeDialogFragment.show(fragmentManager, "RepeatedCodeDialogFragment")
        repeatedCodeDialogFragment.isCancelable = false
    }

    private fun showCorrectCodeDialogFragment() {
        val fragmentManager : FragmentManager = supportFragmentManager
        val correctCodeDialogFragment = CorrectCodeDialogFragment()

        val bundle = Bundle()
        bundle.putInt("completedTaskPointsBundle", doneTask!!.points)
        bundle.putInt("completedTaskTaskNumberBundle", doneTask!!.taskNumber)
        bundle.putString("completedTaskTipsBundle", doneTask!!.description)
        bundle.putString("completedTaskBackgroundImageBungle", doneTask!!.imageSource)
        bundle.putString("completedTaskTitleBundle", doneTask!!.title)
        correctCodeDialogFragment.arguments = bundle

        correctCodeDialogFragment.show(fragmentManager, "CorrectCodeDialogFragment")
        correctCodeDialogFragment.isCancelable = false
    }

    private fun updateUserPointsInFirestore() {
        if (AppData.userID != 0) {
            db = FirebaseFirestore.getInstance()
            db.collection("users").document(AppData.userID.toString()).update("points", AppData.gainedPoints)
            Log.d(LOG_MESSAGE, "Updated points")
        }
    }
}