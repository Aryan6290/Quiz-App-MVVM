package com.example.mathongoassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongoassignment.R
import com.example.mathongoassignment.models.QuestionModel

class QuestionsAdapter() : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {




    inner class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private val diffCallBack = object : DiffUtil.ItemCallback<QuestionModel>(){
        override fun areItemsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ= AsyncListDiffer(this,diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.question_item,parent,false)
                )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        val qs = differ.currentList[position]
        holder.itemView.apply {
            val text = "0${position+1}"
            findViewById<TextView>(R.id.countTV).text= text
            findViewById<TextView>(R.id.questionTV).text= qs.question

            findViewById<TextView>(R.id.hintTV).text= "JEE Mains"
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }
}