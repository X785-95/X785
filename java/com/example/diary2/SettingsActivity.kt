package com.example.diary2

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.Toolbar

class SettingsActivity : BaseActivity() {

    private lateinit var languageRadioGroup: RadioGroup
    private lateinit var englishRadio: RadioButton
    private lateinit var russianRadio: RadioButton

    private lateinit var themeRadioGroup: RadioGroup
    private lateinit var darkRadio: RadioButton
    private lateinit var brightRadio: RadioButton
    private lateinit var retroRadio: RadioButton

    private lateinit var toolbar: Toolbar

    companion object {
        private const val TAG = "SettingsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initializeViews()
        setupToolbar()
        loadCurrentLanguage()
        loadCurrentTheme()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        loadCurrentLanguage()
        loadCurrentTheme()
    }

    private fun initializeViews() {
        languageRadioGroup = findViewById(R.id.languageRadioGroup)
        englishRadio = findViewById(R.id.englishRadio)
        russianRadio = findViewById(R.id.russianRadio)

        themeRadioGroup = findViewById(R.id.ThemeRadioGroup)
        darkRadio = findViewById(R.id.DarkRadio)
        brightRadio = findViewById(R.id.BrightRadio)
        retroRadio = findViewById(R.id.RetroRadio)

        toolbar = findViewById(R.id.toolbar)
        englishRadio.text = getString(R.string.english)
        russianRadio.text = getString(R.string.russian)

        darkRadio.text = getString(R.string.dark)
        brightRadio.text = getString(R.string.bright)
        retroRadio.text = getString(R.string.retro)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun loadCurrentLanguage() {
        val currentLanguage = NotesManager.getCurrentLanguage()
        languageRadioGroup.setOnCheckedChangeListener(null)

        when (currentLanguage) {
            "en" -> englishRadio.isChecked = true
            "ru" -> russianRadio.isChecked = true
        }

        setupClickListeners()
    }

    private fun changeLanguage(language: String) {
        NotesManager.setLanguage(language)
        restartApp()
    }

    private fun loadCurrentTheme() {
        val currentTheme = ThemeManager.getCurrentTheme()
        themeRadioGroup.setOnCheckedChangeListener(null)

        when (currentTheme) {
            ThemeManager.THEME_DARK -> darkRadio.isChecked = true
            ThemeManager.THEME_BRIGHT -> brightRadio.isChecked = true
            ThemeManager.THEME_RETRO -> retroRadio.isChecked = true
        }

        setupClickListeners()
    }

    private fun changeTheme(theme: String) {
        ThemeManager.setTheme(theme)
        restartApp()
    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun setupClickListeners() {
        languageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.englishRadio -> changeLanguage("en")
                R.id.russianRadio -> changeLanguage("ru")
            }
        }
        
        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.DarkRadio -> changeTheme(ThemeManager.THEME_DARK)
                R.id.BrightRadio -> changeTheme(ThemeManager.THEME_BRIGHT)
                R.id.RetroRadio -> changeTheme(ThemeManager.THEME_RETRO)
            }
        }
    }
}