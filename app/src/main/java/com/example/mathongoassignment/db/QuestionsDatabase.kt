package com.example.mathongoassignment.db

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mathongoassignment.models.Option
import com.example.mathongoassignment.models.QuestionModel
import com.example.mathongoassignment.models.Solution

import java.time.chrono.HijrahChronology.INSTANCE


@Database(
    entities = [QuestionModel::class, Option::class,Solution::class],
    version = 1
)
//@TypeConverters(Converters::class)
abstract class QuestionsDatabase : RoomDatabase() {

    abstract fun getQuestionDao(): QuestionsDAO

    companion object{
        @Volatile
        private var instance : QuestionsDatabase? =null





     fun getDatabase(context: Context):QuestionsDatabase{
         if(instance==null){
             synchronized(this){
                 instance=Room.databaseBuilder(
                     context,
                     QuestionsDatabase::class.java,
                     "questionDB"

                 ).allowMainThreadQueries().build()
             }
         }
         return instance!!
     }
    }
}