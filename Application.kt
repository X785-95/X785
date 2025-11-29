package com.example.diary

import android.app.Application
import android.content.res.Configuration
import java.util.Locale

class DiaryApp : Application() {

    companion object {
        private const val TAG = "DiaryApp"
    }

    override fun onCreate() {
        super.onCreate()
        NotesManager.initialize(this)
        setupAppLanguage()
    }

    private fun setupAppLanguage() {
        val currentLanguage = NotesManager.getCurrentLanguage()
        setAppLanguage(currentLanguage)
    }

    fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}