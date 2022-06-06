package com.example.mathongoassignment.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "questions")

class QuestionModel (
    @PrimaryKey
    val id: String,
    val attemptedOption : Int,

    val correctOption: Int,
    val question : String

) :  Serializable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}