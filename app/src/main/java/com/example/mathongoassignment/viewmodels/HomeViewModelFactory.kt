package com.example.mathongoassignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mathongoassignment.repository.QuestionRepository

class HomeViewModelFactory(val questionRepository: QuestionRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(questionRepository) as T
    }
}