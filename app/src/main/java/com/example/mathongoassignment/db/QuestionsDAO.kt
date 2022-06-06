package com.example.mathongoassignment.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mathongoassignment.models.Option
import com.example.mathongoassignment.models.QuestionModel

import com.example.mathongoassignment.models.QuestionModelItem
import com.example.mathongoassignment.models.Solution

@Dao
interface QuestionsDAO  {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addQuestion(q : QuestionModel):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addOption(o : Option): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSolution(o : Solution): Long


   @Query("SELECT * FROM options where questionId=:id")
   suspend fun getOptions(id : String) : List<Option>


    @Query("SELECT * FROM questions WHERE attemptedOption=-1")
  suspend  fun getUnattemptedQuestions(): List<QuestionModel>

    @Query("SELECT * FROM questions WHERE attemptedOption!=-1")
    fun getAttemptedQuestions(): List<QuestionModel>

    @Query("UPDATE questions SET attemptedOption= :option WHERE id=:id" )
    fun updateQuestion(id : String, option : Int)

    @Query("SELECT * FROM solutions  WHERE questionId=:id" )
    fun getSolution(id : String ) : Solution







}