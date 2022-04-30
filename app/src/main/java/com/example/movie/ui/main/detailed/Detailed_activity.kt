package com.example.movie.ui.main.detailed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.util.constants
import com.example.movie.models.movie

class detailed_activity : AppCompatActivity() {
    lateinit var img_det :ImageView
    lateinit var txt_title:TextView
    lateinit var viewModel: movedetaild_viewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        img_det = findViewById(R.id.img_detailed)
        txt_title = findViewById(R.id.txt_title_detailed)
        viewModel = ViewModelProvider(this).get(movedetaild_viewmodel::class.java)
        var data = intent.getSerializableExtra("movie") as movie
        Glide.with(this).load(constants.img_link +data.backdropPath).into(img_det)


            txt_title.text = data.title








    }
}