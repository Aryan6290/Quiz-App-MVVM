package com.example.mathongoassignment.viewmodels

import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathongoassignment.R
import com.example.mathongoassignment.models.Option
import com.example.mathongoassignment.models.QuestionModel
import com.example.mathongoassignment.models.Solution
import com.example.mathongoassignment.repository.QuestionRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

     var selectedItem : MutableLiveData<Int>?=  MutableLiveData(0)

    var answerButtonClicked : MutableLiveData<Boolean>? = MutableLiveData(false)
    var selectedOption : MutableLiveData<Int>?=  MutableLiveData(-1)
    var questionNumber : MutableLiveData<Int>?=  MutableLiveData(-1)
    var selectedQ : MutableLiveData<QuestionModel>?=  MutableLiveData()

    var solution : MutableLiveData<Solution> = MutableLiveData()

    var attemptedQs : MutableLiveData<List<QuestionModel>>?= MutableLiveData();
    var unAttemptedQs : MutableLiveData<List<QuestionModel>>?= MutableLiveData();

    var options : MutableLiveData<List<Option>>?=MutableLiveData()

    init {
        getUnattemptedQs()
        getAttemptedQs()
    }

    fun setAnswerBtnClicked(newVal : Boolean){
        answerButtonClicked?.value=newVal
    }
    fun setSelectedItem(value : Int){
        selectedItem?.value= value;

    }

    fun setSelectedOption(value : Int){
        selectedOption?.postValue(value)
    }
    fun setSelectedQ(value: Int){
        Log.i("wtfie","happen")
        questionNumber?.value=value

        val id =unAttemptedQs?.value?.get(questionNumber?.value!!)?.id

        selectedOption?.value=-1
        selectedQ?.value= unAttemptedQs?.value?.get(questionNumber?.value!!)



        Log.i("wtfid",id.toString())
        getOptions(id)
        getSolution(id!!)


    }

    private fun getAttemptedQs() {
        viewModelScope.launch {
            val q =questionRepository.getAttemptedQuestions()
            attemptedQs=q
            if(q.value!=null && q.value?.size!=0){
                val id = attemptedQs?.value?.get(0)?.id
                val o = questionRepository.getOptions(id!!)

                options=o
            }

        }


    }

    private fun getUnattemptedQs() {

        viewModelScope.launch {
            val q=  questionRepository.getUnattemptedQuestions()
            Log.i("viewm", q.value.toString())
            unAttemptedQs=q
            if(q.value!=null && q.value?.size!=0) {
                val id = q?.value?.get(0)?.id
                val o = questionRepository.getOptions(id!!)

                options=o
            }



            if(unAttemptedQs!=null){
                Log.i("viewma", unAttemptedQs?.value?.size.toString())
            }else{
                Log.i("viewno", unAttemptedQs?.value?.size.toString())
            }


        }




    }

    fun changeQ(){

        answerButtonClicked?.value=false
        selectedOption?.value=-1
        selectedQ?.value= unAttemptedQs?.value?.get(questionNumber?.value!!)


        getOptions(selectedQ?.value?.id)

        getSolution(selectedQ?.value?.id!!)




    }

    fun getItem(): MutableLiveData<Int>? {
        return selectedItem;
    }

    fun incrementQ(){
        if(questionNumber?.value!=unAttemptedQs?.value?.size){
            Log.i("yoo","clicked")
            questionNumber?.value= questionNumber?.value?.plus(1)
            changeQ()
        }


    }
    fun decrementQ(){
        if(questionNumber?.value!=0){
            Log.i("yoo","clicked")
            questionNumber?.value= questionNumber?.value?.minus(1)
            changeQ()
        }

    }

    fun getOptions(id: String?) {
        if (id != null) {
            Log.i("id",id)
        }
            var o : MutableLiveData<List<Option>> = MutableLiveData()
        viewModelScope.launch {
            o = questionRepository.getOptions(id!!)

            for(i in o.value!!){

                Log.i("options",i.text)
            }

            options?.value=o.value

        }

    }

    fun getSolution(id : String){
        var s : MutableLiveData<Solution> =MutableLiveData()

        viewModelScope.launch {
            s = questionRepository.getSolution(id)

            solution.value =s.value

        }
    }

    fun updateQuestion(id:String,chosenOption : Int){
        var a = questionRepository.updateQuestion(chosenOption, id)

    }




}