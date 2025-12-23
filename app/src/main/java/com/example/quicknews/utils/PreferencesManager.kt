package com.example.quicknews.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("QuickNewsPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
        private const val KEY_THEME = "theme"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
        const val THEME_CUSTOM = "custom"
    }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var username: String?
        get() = prefs.getString(KEY_USERNAME, null)
        set(value) = prefs.edit().putString(KEY_USERNAME, value).apply()

    var theme: String
        get() = prefs.getString(KEY_THEME, THEME_LIGHT) ?: THEME_LIGHT
        set(value) = prefs.edit().putString(KEY_THEME, value).apply()

    fun logout() {
        prefs.edit().clear().apply()
    }
}