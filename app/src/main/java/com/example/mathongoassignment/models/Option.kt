package com.example.mathongoassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "options")
data class Option(
    @PrimaryKey
    val id: String,

    val isCorrect: Boolean,
    val text: String,

    val questionId : String
)