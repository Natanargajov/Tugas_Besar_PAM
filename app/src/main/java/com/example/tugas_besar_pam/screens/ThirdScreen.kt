package com.example.tugas_besar_pam.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tugas_besar_pam.R
import com.google.android.material.button.MaterialButton

class ThirdScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)
        val finish = view.findViewById<MaterialButton>(R.id.button_OnBoard3)

        finish.setOnClickListener{
            findNavController().navigate(R.id.action_onBoardingFragment_to_registerActivity)
            onBoardingIsFinished()
        }
        return view
    }

    private fun onBoardingIsFinished() {
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("finished", true)
        editor.apply()
    }
}