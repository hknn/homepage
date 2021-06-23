package com.example.homepage.api.sp

import android.content.SharedPreferences

class PreferenceRepository(private val sharedPreferences: SharedPreferences) {

    var token: String? = ""
        get() = sharedPreferences.getString(PREFERENCE_TOKEN, "")
        set(value) {
            sharedPreferences.edit().putString(PREFERENCE_TOKEN, value).apply()
            field = value
        }

    companion object {
        private const val PREFERENCE_TOKEN = "preference_token"
    }
}