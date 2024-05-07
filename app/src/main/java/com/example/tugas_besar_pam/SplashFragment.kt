package com.example.tugas_besar_pam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Inisialisasi ProgressBar
        progressBar = view.findViewById(R.id.progress_bar)

        // Inisialisasi NavController
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)

        // Inisialisasi animasi untuk logo_eatera
        val logoAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.from_top)
        view.findViewById<View>(R.id.logo_eatera).startAnimation(logoAnimation)

        // Set durasi untuk animasi logo_eatera
        val logoAnimationDuration = 1500L

        // Set durasi yang dibutuhkan untuk mencapai kemajuan 100
        val progressDuration = 3000L
        val progressInterval = 100L
        val totalSteps = (progressDuration / progressInterval).toInt()

        // Menunda tampilan ProgressBar setelah animasi logo_eatera selesai
        Handler(Looper.getMainLooper()).postDelayed({

            // Jalankan proses dengan interval tertentu untuk memperbarui ProgressBar
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object : Runnable {
                var currentStep = 0

                override fun run() {
                    progressBar.progress = ((currentStep.toFloat() / totalSteps.toFloat()) * 100).toInt()
                    if (currentStep < totalSteps) {
                        currentStep++
                        handler.postDelayed(this, progressInterval)
                    } else {
                        if (onBoardingIsFinished()){
                            findNavController().navigate(R.id.action_splashFragment_to_loginActivity)
                            //findNavController().navigate(R.id.action_splashFragment_to_registerActivity) # Ubah kalo dah clear semua
                        } else {
                            findNavController().navigate(R.id.navigation_splashFragment_to_onBoardingFragment)
                        }

                    }
                }
            }, progressInterval)
        }, logoAnimationDuration)

        return view
    }

    private fun onBoardingIsFinished(): Boolean{
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }
}
