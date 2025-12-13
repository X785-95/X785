package com.example.diary2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

class EditNoteActivity : BaseActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var toolbar: Toolbar

    private var currentNote: Note? = null

    companion object {
        private const val TAG = "EditNoteActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        initializeViews()
        setupToolbar()
        loadNoteData()
        setupClickListeners()
    }

    private fun initializeViews() {
        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)
        toolbar = findViewById(R.id.toolbar)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.edit_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun loadNoteData() {
        currentNote = intent.getSerializableExtra("note") as? Note
        currentNote?.let { note ->
            titleEditText.setText(note.title)
            contentEditText.setText(note.content)
        }
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            saveNote()
        }

        deleteButton.setOnClickListener {
            deleteNote()
        }
    }

    private fun saveNote() {
        val title = titleEditText.text.toString().trim()
        val content = contentEditText.text.toString().trim()

        currentNote?.let { note ->
            note.title = title
            note.content = content
            NotesManager.saveNote(note)
        }
        finish()
    }

    private fun deleteNote() {
        currentNote?.let { note ->
            NotesManager.deleteNote(note.id)
        }
        finish()

    }
}