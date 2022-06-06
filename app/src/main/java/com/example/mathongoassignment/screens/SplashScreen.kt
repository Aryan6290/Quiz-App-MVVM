package com.example.mathongoassignment.screens

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mathongoassignment.R
import java.util.*


class SplashScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashScreen_to_mainFragment)


        }, 5000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)


    }


}