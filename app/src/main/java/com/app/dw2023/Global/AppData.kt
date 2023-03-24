package com.app.dw2023.Global

import com.app.dw2023.Model.Event
import com.app.dw2023.Model.Task

const val LOG_MESSAGE = "verySecretMessage"

const val HOME_FRAGMENT_INDEX = 0
const val EVENTS_FRAGMENT_INDEX = 1
const val TASKS_FRAGMENT_INDEX = 3
const val SETTINGS_FRAGMENT_INDEX = 4

const val ID_MAX_VALUE = 10000000

const val PREF_LAST_SELECTED_FRAGMENT_INDEX = "last_selected_fragment_index"
const val PREF_NAME = "my_shared_pref"
const val PREF_ACTIVITY_AFTER_SCANNER = "key_activity_after_scanner"
const val PREF_QR_CODES = "qr_codes_key"
const val PREF_GAINED_POINTS = "gained_points_key"
const val PREF_USER_ID = "user_id_key"
const val PREF_DIRTY_POINTS = "dirty_points_key"

object AppData {
    var lastSelectedIndex = 0
    var loadedQrCodes = mutableSetOf<String>()
    var tasksList = arrayListOf<Task>()
    var validQRCodes = listOf<String>()
    var pointsList = listOf<Int>()
    var gainedPoints = 0
    var eventList = arrayListOf<Event>()
    var userID = 0

    var afterScanner = false
    var successfullySavedPoints = true
}