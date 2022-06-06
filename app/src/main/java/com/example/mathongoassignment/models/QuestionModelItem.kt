package com.example.mathongoassignment.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class QuestionModelItem(
    val approximateTimeRequired: Any,
    val category: Any,
    val chapters: List<String>,
    val classes: List<Any>,
    val concepts: List<Any>,
    val correctValue: Any,

    val attemptedOption : Int,
    val exams: List<String>,
    val helperText: Any,
    @PrimaryKey
    val id: String,
    val imageBaseUrl: Any,
    val level: Any,
    val microConcepts: List<Any>,

    val options: List<Option>,
    val previousYearPapers: List<String>,

    val question: Question,
    val questionLabels: List<Any>,
    val solution: Solution,
    val source: String,
    val subjects: List<String>,
    val topics: List<String>,
    val type: String,
    val videoSolution: Any
) : Serializable