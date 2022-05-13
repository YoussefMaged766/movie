package com.example.movie.ui.main.detailed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.adapter.adapter_page

import com.example.movie.databinding.FragmentDetailedBinding
import com.example.movie.models.movie
import com.example.movie.util.constants
import java.util.ArrayList


class detailedFragment : Fragment() {

lateinit var binding: FragmentDetailedBinding
lateinit var viewModel:movedetaild_viewmodel
lateinit var data:movie


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
   data = it.getSerializable("movie_details") as movie
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_detailed, container, false)


        viewModel = ViewModelProvider(this).get(movedetaild_viewmodel::class.java)

        Glide.with(this).load(constants.img_link +data.backdropPath).into(binding.imgDetailed)
        binding.txtTitleDetailed.text = data.title


        return binding.root
    }



}