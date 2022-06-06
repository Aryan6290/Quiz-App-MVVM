package com.example.mathongoassignment.screens

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mathongoassignment.R
import com.example.mathongoassignment.databinding.FragmentMainBinding
import com.example.mathongoassignment.databinding.FragmentQuizBinding
import com.example.mathongoassignment.db.QuestionsDatabase
import com.example.mathongoassignment.repository.QuestionRepository
import com.example.mathongoassignment.viewmodels.HomeViewModel
import com.google.android.material.card.MaterialCardView
import org.w3c.dom.Text


class QuizFragment : Fragment() {
    lateinit var binding: FragmentQuizBinding
    lateinit var model : HomeViewModel


     val args  by navArgs<QuizFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        Log.i("qsName",qs.question)
         binding = FragmentQuizBinding.inflate(inflater,container,false)

        model= ViewModelProvider(requireActivity()).get(HomeViewModel::class.java);

        binding.model=model
        binding.lifecycleOwner= this

        val qs= args.question
        binding.root.findViewById<TextView>(R.id.qsStatementTV).text=qs.question

  return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("hello",args.question.question)

        val firstOptionContainer= view.findViewById<MaterialCardView>(R.id.firstOptionContainer)
        val secondOptionContainer= view.findViewById<MaterialCardView>(R.id.secondOptionContainer)
        val thirdOptionContainer= view.findViewById<MaterialCardView>(R.id.thirdOptionContainer)
        val fourthOptionContainer= view.findViewById<MaterialCardView>(R.id.fourthOptionContainer)

        val answerContainer=view.findViewById<TextView>(R.id.solutionText)
        val answerHeader=view.findViewById<TextView>(R.id.solutionHeader)

        val qs= args.question

        val inflater: LayoutInflater = LayoutInflater.from(activity).context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater





        view.findViewById<MaterialCardView>(R.id.incrementImage).setOnClickListener(View.OnClickListener {
            answerContainer.visibility=View.GONE
            answerHeader.visibility=View.GONE
            model.incrementQ()




        })
        view.findViewById<MaterialCardView>(R.id.decrementImage).setOnClickListener(View.OnClickListener {
            answerContainer.visibility=View.GONE
            answerHeader.visibility=View.GONE
            model.decrementQ()

        })

        view.findViewById<MaterialCardView>(R.id.checkAnswerBtn).setOnClickListener {

            model.updateQuestion(id = model.selectedQ?.value?.id!! , chosenOption = model.selectedOption?.value!!)
            model.setAnswerBtnClicked(true)
               if(model.options?.value?.get(model.selectedOption?.value!!)?.isCorrect == true){

                   when(model.selectedOption?.value){
                       0 -> firstOptionContainer.strokeColor=Color.GREEN
                       1-> secondOptionContainer.strokeColor=Color.GREEN
                       2-> thirdOptionContainer.strokeColor=Color.GREEN
                       3-> fourthOptionContainer.strokeColor=Color.GREEN
                   }
                   showPopup(view)

               }else{

                   when(model.selectedOption?.value){
                       0 -> firstOptionContainer.strokeColor=Color.RED
                       1-> secondOptionContainer.strokeColor=Color.RED
                       2-> thirdOptionContainer.strokeColor=Color.RED
                       3-> fourthOptionContainer.strokeColor=Color.RED
                   }


               }
            when(model.selectedQ?.value?.correctOption){
                0 -> firstOptionContainer.strokeColor=Color.GREEN
                1-> secondOptionContainer.strokeColor=Color.GREEN
                2-> thirdOptionContainer.strokeColor=Color.GREEN
                3-> fourthOptionContainer.strokeColor=Color.GREEN
            }

            answerContainer.apply {
                text= model.solution.value?.text

            }


        }

        firstOptionContainer.setOnClickListener {
            model.setSelectedOption(0)
        }
        secondOptionContainer.setOnClickListener {
            model.setSelectedOption(1)
        }
        thirdOptionContainer.setOnClickListener {
            model.setSelectedOption(2)
        }
        fourthOptionContainer.setOnClickListener {
            model.setSelectedOption(3)
        }

        val db = QuestionsDatabase.getDatabase(requireContext().applicationContext)



        model.selectedQ?.observe(viewLifecycleOwner){
            var number = model.questionNumber?.value!! +1
            view.findViewById<TextView>(R.id.questionNumberTV).text= "Q${number} (Single Correct)"


            view.findViewById<TextView>(R.id.qsStatementTV).text=it.question



        }

        model.selectedOption?.observe(viewLifecycleOwner){
            Log.i("change",it.toString())
        }

        model.options?.observe(viewLifecycleOwner){

            view.findViewById<TextView>(R.id.optionOneTV).text= it?.get(0)?.text
            view.findViewById<TextView>(R.id.optionTwoTV).text= it?.get(1)?.text
            view.findViewById<TextView>(R.id.optionThreeTV).text= it?.get(2)?.text
            view.findViewById<TextView>(R.id.optionFourTV).text=it?.get(3)?.text
        }








    }
    fun showPopup( v: View){
        val viewPopupWindow=layoutInflater.inflate(R.layout.popup_window_top,null)
        val popupWindow = PopupWindow(viewPopupWindow, v.layoutParams.width,300,true)
        popupWindow.showAtLocation(view,Gravity.TOP,0,0)

        Handler().postDelayed({
            popupWindow.dismiss()
        }, 2000)
    }
}


