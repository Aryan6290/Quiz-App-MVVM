package com.example.mathongoassignment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mathongoassignment.db.QuestionsDatabase
import com.example.mathongoassignment.models.Option
import com.example.mathongoassignment.models.QuestionModel
import com.example.mathongoassignment.models.QuestionModelItem
import com.example.mathongoassignment.models.Solution

class QuestionRepository(private val db : QuestionsDatabase) {


    suspend fun upsert(questionModel: QuestionModelItem){

        var x =0;
        var t=0;
        for(i in questionModel.options){
            if(!i.isCorrect){
                t++
            }else{
                x=t;
            }
            val o = Option(id = i.id, isCorrect = i.isCorrect, text=i.text, questionId = questionModel.id)

            db.getQuestionDao().addOption(o)
        }
        val q = QuestionModel(id = questionModel.id, question = questionModel.question.text, attemptedOption = -1, correctOption = x)
        val a= db.getQuestionDao().addQuestion(q)


            val s = Solution(questionId = questionModel.id, image = questionModel.solution.image, text = questionModel.solution.text )
            db.getQuestionDao().addSolution(s)

    }

    fun getAttemptedQuestions(): MutableLiveData<List<QuestionModel>> {
        val qs : MutableLiveData<List<QuestionModel>> = MutableLiveData()

        qs.value=db.getQuestionDao().getAttemptedQuestions()
        return  qs
    }

    suspend fun getUnattemptedQuestions(): MutableLiveData<List<QuestionModel>> {
        val qs : MutableLiveData<List<QuestionModel>> = MutableLiveData()

        qs.value=db.getQuestionDao().getUnattemptedQuestions()
        return  qs
    }

    suspend fun getOptions(id : String): MutableLiveData<List<Option>> {
        var os : MutableLiveData<List<Option>> = MutableLiveData()
        os.value = db.getQuestionDao().getOptions(id)



        return os
    }
    suspend fun getSolution(id : String): MutableLiveData<Solution> {
        var s : MutableLiveData<Solution> = MutableLiveData()
        s.value = db.getQuestionDao().getSolution(id)



        return s
    }

    fun updateQuestion(option : Int, id : String){
        var q = db.getQuestionDao().updateQuestion(id,option)

    }

}