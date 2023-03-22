package com.app.dw2023.Model

import com.google.firebase.Timestamp

data class User(var id: Int = 0, var points: Int = 0, var time: Timestamp? = null, var winner: Boolean = false)