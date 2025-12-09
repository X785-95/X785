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
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        loadCurrentLanguage()
    }

    private fun initializeViews() {
        languageRadioGroup = findViewById(R.id.languageRadioGroup)
        englishRadio = findViewById(R.id.englishRadio)
        russianRadio = findViewById(R.id.russianRadio)
        toolbar = findViewById(R.id.toolbar)
        englishRadio.text = getString(R.string.english)
        russianRadio.text = getString(R.string.russian)
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
    }
}