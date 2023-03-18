package com.app.dw2023.Model

data class Task(var title: String? = null, var points: Int = 0, var description: String? = null, val imageSource: String? = null, val qrCode: String? = null, var isDone: Boolean = false, var taskNumber: Int = 0)