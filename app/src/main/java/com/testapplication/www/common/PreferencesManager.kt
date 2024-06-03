package com.testapplication.www.common

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveUserId(userId: Long?) {
        val editor = sharedPreferences.edit()
        if (userId != null) {
            editor.putLong("userId", userId)
        } else {
            // Clear the userId if it's null
            editor.remove("userId")
        }
        editor.apply()
    }

    fun getUserId(defaultValue: Int): Long {
        return sharedPreferences.getLong("userId", defaultValue.toLong())
    }

    fun clearPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    // Save CheckIn status
    fun saveCheckInStatus(isCheckedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("checkInStatus", isCheckedIn)
        editor.apply()
    }

    // Get CheckIn status
    fun getCheckInStatus(defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean("checkInStatus", defaultValue)
    }
}
