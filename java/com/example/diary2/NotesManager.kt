package com.example.diary2

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

object NotesManager {
    private const val TAG = "NotesManager"
    private const val PREFS_NAME = "notes_prefs"
    private const val NOTES_KEY = "notes"
    private const val LANGUAGE_KEY = "app_language"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getNotes(): List<Note> {
        if (!::sharedPreferences.isInitialized) {
            return emptyList()
        }

        return try {
            val notesJson = sharedPreferences.getString(NOTES_KEY, "[]") ?: "[]"
            val notes = mutableListOf<Note>()

            if (notesJson.isNotEmpty()) {
                val jsonArray = JSONArray(notesJson)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val note = Note(
                        id = jsonObject.optLong("id", System.currentTimeMillis()),
                        title = jsonObject.optString("title", ""),
                        content = jsonObject.optString("content", ""),
                        createdAt = jsonObject.optLong("createdAt", System.currentTimeMillis())
                    )
                    notes.add(note)
                }
            }
            notes
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveNote(note: Note) {
        if (!::sharedPreferences.isInitialized) {
            Log.e(TAG, "NotesManager not initialized!")
            return
        }

        val notes = getNotes().toMutableList()
        val existingIndex = notes.indexOfFirst { it.id == note.id }

        if (existingIndex != -1) {
            notes[existingIndex] = note
            Log.d(TAG, "Updated existing note: ${note.title}")
        } else {
            notes.add(note)
            Log.d(TAG, "Added new note: ${note.title}")
        }

        saveNotes(notes)
    }

    fun deleteNote(noteId: Long) {
        val notes = getNotes().toMutableList()
        val removed = notes.removeAll { it.id == noteId }
        if (removed) {
            saveNotes(notes)
        }
    }

    private fun saveNotes(notes: List<Note>) {
        val jsonArray = JSONArray()
        notes.forEach { note ->
            val jsonObject = JSONObject().apply {
                put("id", note.id)
                put("title", note.title)
                put("content", note.content)
                put("createdAt", note.createdAt)
            }
            jsonArray.put(jsonObject)
        }

        sharedPreferences.edit().putString(NOTES_KEY, jsonArray.toString()).apply()
    }

    fun getCurrentLanguage(): String {
        return if (!::sharedPreferences.isInitialized) {
            "en"
        } else {
            sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
        }
    }

    fun applyLanguageToApp(context: Context) {
        val language = getCurrentLanguage()
        val app = context.applicationContext as? DiaryApp
        app?.setAppLanguage(language)
    }
    fun setLanguage(language: String) {
        sharedPreferences.edit().putString(LANGUAGE_KEY, language).apply()
        val saved = sharedPreferences.getString(LANGUAGE_KEY, "en")
    }
}