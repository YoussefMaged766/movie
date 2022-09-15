package com.example.movie.ui.main.intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.movie.R
import com.example.movie.adapter.SlidingAdapter
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.databinding.ActivitySigninBinding
import com.example.movie.models.SlidingDataIntro
import com.example.movie.ui.main.MainActivity

class Signin_Activity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding
    lateinit var adapter: SlidingAdapter
    lateinit var prefrence: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefrence = getSharedPreferences("intro", Context.MODE_PRIVATE)
        if (!prefrence.getBoolean("pref_show_intro",true)){
            startActivity( Intent(applicationContext, MainActivity:: class.java))
            finish()
        }

        adapter = SlidingAdapter(
            listOf(
                SlidingDataIntro(R.raw.intro2, ""),
                SlidingDataIntro(R.raw.intro3, ""),
                SlidingDataIntro(R.raw.share, ""),
                SlidingDataIntro(R.raw.trend, "")
            )
        )
        binding.viewpager.adapter = adapter
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

        })



        binding.btnNext.setOnClickListener {

            if (binding.viewpager.currentItem + 1 < adapter.itemCount) {
                binding.viewpager.currentItem +=1
            }else{
                Intent(applicationContext, MainActivity:: class.java).also {
                    startActivity(it)
                    val editor = prefrence.edit()
                    editor.putBoolean("pref_show_intro",false)
                    editor.apply()
                    finish()
                }
            }

        }
    }
}