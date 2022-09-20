package com.example.movie.ui.main.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.example.movie.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var logo  = findViewById<ImageView>(R.id.imgSplash)
        logo.alpha=0f
        logo.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this,Signin_Activity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

    }
}