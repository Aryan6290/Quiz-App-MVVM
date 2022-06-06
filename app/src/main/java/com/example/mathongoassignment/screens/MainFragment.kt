package com.example.mathongoassignment.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathongoassignment.R
import com.example.mathongoassignment.adapters.QuestionsAdapter
import com.example.mathongoassignment.databinding.FragmentMainBinding
import com.example.mathongoassignment.utils.RecyclerItemClickListener
import com.example.mathongoassignment.viewmodels.HomeViewModel


class MainFragment : Fragment() {

    var selectedOption =0;
    lateinit var viewModel : HomeViewModel
    lateinit var unqsAdapter: QuestionsAdapter
    lateinit var qsAdapter: QuestionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentMainBinding.inflate(inflater,container,false)


        // Inflate the layout for this fragment

        Handler(Looper.getMainLooper()).postDelayed({

            binding.selectionContainer.visibility=View.VISIBLE
            binding.shimmerView.visibility= View.GONE
            binding.unattemptedRV.visibility=View.VISIBLE
        }, 2000)
        viewModel= ViewModelProvider(requireActivity()).get(HomeViewModel::class.java);

        viewModel.setSelectedItem(0)





        binding.firstOptionTV.setOnClickListener{

            viewModel.setSelectedItem(0)

            binding.unattemptedRV.visibility=View.VISIBLE

            binding.attemptedRV.visibility=View.GONE

        }

        binding.secondOptionTV.setOnClickListener{

            viewModel.setSelectedItem(1)
            binding.unattemptedRV.visibility=View.GONE

            binding.attemptedRV.visibility=View.VISIBLE
        }
        fun onClick(position : Int){
           viewModel.setSelectedQ(position)



            val action= viewModel.unAttemptedQs?.value?.get(position)
                ?.let { MainFragmentDirections.actionMainFragmentToQuizFragment(it) }

            if (action != null) {
                findNavController().navigate(action)
            }
        }





        binding.model=viewModel

        binding.lifecycleOwner= this
        unqsAdapter= QuestionsAdapter()
        qsAdapter= QuestionsAdapter()
        binding.unattemptedRV.apply {
            adapter=unqsAdapter
            layoutManager=LinearLayoutManager(activity)

        }
        binding.unattemptedRV.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.unattemptedRV,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        // do whatever
                        onClick(position)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // do whatever
                    }
                })
        )

        binding.attemptedRV.apply {
            adapter=qsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

        viewModel.unAttemptedQs?.observe(viewLifecycleOwner) {
            if(it!=null){
                if(it.isEmpty() && selectedOption==0){
                    binding.unattemptedRV.visibility=View.VISIBLE
                }else{
                    binding.unattemptedRV.visibility=View.GONE
                }
                Log.i("siz", it.size.toString())
                unqsAdapter.differ?.submitList(it)
            }else{
                Log.i("woo", "why")
            }

        }


        viewModel.attemptedQs?.observe(viewLifecycleOwner) {

            if(it!=null){
                if(it.isEmpty() && selectedOption==1){
                    binding.attemptedRV.visibility=View.VISIBLE
                }else{
                    binding.attemptedRV.visibility=View.GONE
                }
                Log.i("siz", it.size.toString())
                qsAdapter.differ?.submitList(it)
            }else{
                Log.i("woo", "why")
            }


        }




        return binding.root
    }















}