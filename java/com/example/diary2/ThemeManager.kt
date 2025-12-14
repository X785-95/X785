package com.example.diary2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val THEME_KEY = "AppTheme"

    const val THEME_BRIGHT = "bright"
    const val THEME_DARK = "dark"
    const val THEME_RETRO = "retro"

    lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveTheme(theme: String) {
        if (!::sharedPreferences.isInitialized) {
            return
        }
        sharedPreferences.edit().putString(THEME_KEY, theme).apply()
    }

    fun getCurrentTheme(): String {
        val theme = sharedPreferences.getString(THEME_KEY, THEME_BRIGHT) ?: THEME_BRIGHT
        return when (theme.lowercase()) {
            "dark" -> THEME_DARK
            "retro" -> THEME_RETRO
            else -> THEME_BRIGHT
        }
    }

    fun setTheme(theme: String) {
        saveTheme(theme)
    }
}