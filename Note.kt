package com.example.diary

import java.io.Serializable

data class Note(
    val id: Long = System.currentTimeMillis(),
    var title: String = "",
    var content: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Serializable