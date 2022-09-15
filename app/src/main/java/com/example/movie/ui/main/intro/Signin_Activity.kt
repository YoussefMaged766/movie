package com.example.movie.ui.main.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.example.movie.R
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.databinding.ActivitySigninBinding
import com.example.movie.ui.main.MainActivity

class Signin_Activity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieImg.setAnimation(R.raw.intro2)
        binding.lottieImg.playAnimation()


        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}