package com.example.diary2

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateBaseContextLocale(newBase))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val language = NotesManager.getCurrentLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        } else {
            configuration.setLocale(locale)
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
        return context
    }



    override fun onResume() {
        super.onResume()
        val currentLanguage = NotesManager.getCurrentLanguage()
        val configLanguage = resources.configuration.locales[0].language
        if (currentLanguage != configLanguage) {
            recreate()
        }
    }
}