package com.example.diary2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import java.time.LocalDate

class AddNoteActivity : BaseActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var toolbar: Toolbar

    companion object {
        private const val TAG = "AddNoteActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        initializeViews()
        setupToolbar()
        setupClickListeners()
    }

    private fun initializeViews() {
        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        saveButton = findViewById(R.id.saveButton)
        toolbar = findViewById(R.id.toolbar)
        titleEditText.hint = LocalDate.now().toString()
        contentEditText.hint = getString(R.string.note_content_hint)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = titleEditText.text.toString().trim()
        val title1 = LocalDate.now().toString().trim()
        val content = contentEditText.text.toString().trim()

        if (title.isEmpty() && content.isEmpty()) {
            finish()
            return
        }

        if (title.isEmpty()){
            val note = Note(title = title1, content = content)
            NotesManager.saveNote(note)
        }else{
            val note = Note(title = title, content = content)
            NotesManager.saveNote(note)
        }
        finish()
    }
}