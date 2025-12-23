package com.example.quicknews.utils

import android.app.Activity
import com.example.quicknews.R

object ThemeManager {

    fun applyTheme(activity: Activity, theme: String) {
        when (theme) {
            PreferencesManager.THEME_LIGHT -> {
                activity.setTheme(R.style.Theme_QuickNews_Light)
            }
            PreferencesManager.THEME_DARK -> {
                activity.setTheme(R.style.Theme_QuickNews_Dark)
            }
            PreferencesManager.THEME_CUSTOM -> {
                activity.setTheme(R.style.Theme_QuickNews_Custom)
            }
        }
    }
}