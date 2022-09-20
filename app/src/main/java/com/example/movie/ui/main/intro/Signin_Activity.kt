package com.example.movie.ui.main.intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.movie.R
import com.example.movie.adapter.SlidingAdapter
import com.example.movie.databinding.ActivitySigninBinding
import com.example.movie.models.SlidingDataIntro
import com.example.movie.ui.main.MainActivity
import com.example.movie.util.FadeOutTransformation

class Signin_Activity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding
    lateinit var adapter: SlidingAdapter
    lateinit var prefrence: SharedPreferences
    var fadeOutTransformation = FadeOutTransformation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefrence = getSharedPreferences("intro", Context.MODE_PRIVATE)
        if (!prefrence.getBoolean("pref_show_intro", true)) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        adapter = SlidingAdapter(
            listOf(
                SlidingDataIntro(R.raw.intro2, "Amazing Watching"),
                SlidingDataIntro(R.raw.search, "Search All Movies And TV"),
                SlidingDataIntro(R.raw.share2, "Share your movie on Social media"),
                SlidingDataIntro(R.raw.watchhing, "Watching Trailer on Youtube"),
                SlidingDataIntro(R.raw.trend, "Get Trend Movies Everyday")
            )
        )
        binding.viewpager.adapter = adapter
        binding.viewpager.setPageTransformer(fadeOutTransformation)
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicators(position)
            }

        })
        indicators()



        binding.btnNext.setOnClickListener {

            if (binding.viewpager.currentItem + 1 < adapter.itemCount) {
                binding.viewpager.currentItem += 1
            } else {
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                    val editor = prefrence.edit()
                    editor.putBoolean("pref_show_intro", false)
                    editor.apply()
                    finish()
                }
            }

            if (binding.viewpager.currentItem ==4){
                binding.btnNext.setImageResource(R.drawable.ic_baseline_check_24)
            }
        }

        binding.btnSkip.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            val editor = prefrence.edit()
            editor.putBoolean("pref_show_intro", false)
            editor.apply()
            finish()
        }


    }

    fun indicators() {

        val indicator = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutparams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        layoutparams.setMargins(15, 0, 10, 0)
        for (i in indicator.indices) {
            indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.inactive
                    )
                )
                this?.layoutParams = layoutparams
            }
            binding.indicatorContainer.addView(indicator[i])


        }
    }

    fun setCurrentIndicators(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView =
                binding.indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.inactive
                    )
                )
            }
        }
    }

}