package com.example.mathongoassignment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mathongoassignment.db.QuestionsDatabase
import com.example.mathongoassignment.models.QuestionModelItem
import com.example.mathongoassignment.repository.QuestionRepository
import com.example.mathongoassignment.screens.MainFragment
import com.example.mathongoassignment.viewmodels.HomeViewModel
import com.example.mathongoassignment.viewmodels.HomeViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val db = QuestionsDatabase.getDatabase(this)

        val questionRepository= QuestionRepository(db)
        val viewModelProviderFactory=HomeViewModelFactory(questionRepository)

        if(!sharedPref.contains("there")){

            val qs: List<QuestionModelItem> = readRawJson(R.raw.data)

            GlobalScope.launch {
                for(i in qs){
                    questionRepository.upsert(i)

                }
            }
            val myEdit: SharedPreferences.Editor = sharedPref.edit()
            myEdit.putBoolean("there", true)

            myEdit.apply()
        }else{


        }

        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(HomeViewModel::class.java)


        supportActionBar?.hide()
    }

    private inline fun <reified T> readRawJson(@RawRes rawResId: Int): T {
        resources.openRawResource(rawResId).bufferedReader().use {
            return Gson().fromJson<T>(it, object: TypeToken<T>() {}.type)
        }
    }
}