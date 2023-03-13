package com.app.dw2023.Model

import com.google.firebase.Timestamp

data class Event(var imageSource: String? = null, var partner: String? = null, var timeStart: Timestamp? = null, var timeEnd: Timestamp? = null, var title: String? = null, var eventType: String = "lecture")