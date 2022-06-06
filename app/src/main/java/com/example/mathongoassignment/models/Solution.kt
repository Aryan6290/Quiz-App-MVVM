package com.example.mathongoassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solutions")
data class Solution(
    @PrimaryKey
    val questionId : String,
    val image: String?,

    val text: String
)