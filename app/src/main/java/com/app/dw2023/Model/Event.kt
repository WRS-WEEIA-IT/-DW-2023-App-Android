package com.app.dw2023.Model

import com.google.firebase.Timestamp

data class Event(var company: String? = null, var desc: String? = null, var date: Timestamp? = null)