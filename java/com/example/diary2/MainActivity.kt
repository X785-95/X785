package com.example.diary2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class MainActivity : BaseActivity() {

    private lateinit var notesContainer: LinearLayout
    private lateinit var emptyStateText: TextView
    private lateinit var toolbar: Toolbar

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        setupToolbar()
        loadNotes()
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun initializeViews() {
        notesContainer = findViewById(R.id.notesContainer)
        emptyStateText = findViewById(R.id.emptyStateText)
        toolbar = findViewById(R.id.toolbar)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        try {
            menuInflater.inflate(R.menu.main_menu, menu)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
            when (item.itemId) {
                R.id.action_add -> {
                    startActivity(Intent(this, AddNoteActivity::class.java))
                    true
                }
                R.id.action_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun loadNotes() {
        try {
            notesContainer.removeAllViews()
            val notes = NotesManager.getNotes()

            if (notes.isEmpty()) {
                emptyStateText.visibility = View.VISIBLE
                emptyStateText.text = getString(R.string.no_notes)
            } else {
                emptyStateText.visibility = View.GONE
                for (note in notes.reversed()){
                    addNoteToView (note)
                }
            }
        } catch (e: Exception) {
            emptyStateText.visibility = View.VISIBLE
            emptyStateText.text = "Error loading notes"
        }
    }

    private fun addNoteToView(note: Note) {
        val noteView = layoutInflater.inflate(R.layout.note_item, notesContainer, false)
        val titleTextView = noteView.findViewById<TextView>(R.id.noteTitle)
        val contentTextView = noteView.findViewById<TextView>(R.id.noteContent)
        titleTextView.text = note.title.ifEmpty { getString(R.string.title) }
        contentTextView.text = note.content.ifEmpty { getString(R.string.content) }

        noteView.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("note", note)
            startActivity(intent)
        }

        notesContainer.addView(noteView)
    }
}