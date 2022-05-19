package com.example.movie.ui.main.detailed

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.databinding.FragmentDetailedBinding

import com.example.movie.models.movie
import com.example.movie.ui.main.home.homefragment_viewmodel
import com.example.movie.util.constants


class detailedFragment : Fragment() {

    lateinit var binding: FragmentDetailedBinding
    lateinit var viewModel: movedetaild_viewmodel
    lateinit var viewmodel2: homefragment_viewmodel
    lateinit var data: movie


    var hashMap: HashMap<Int, String> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable("movie_details") as movie
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed, container, false)


        viewModel = ViewModelProvider(this).get(movedetaild_viewmodel::class.java)
        viewmodel2 = ViewModelProvider(this).get(homefragment_viewmodel::class.java)

        Glide.with(this).load(constants.img_link + data.backdropPath).into(binding.imgDetailed)
        Glide.with(this).load(constants.img_link + data.posterPath).into(binding.imgDetailedPoster)
        binding.txtTitleDetailed.text = data.title
        append_genre()
        image_heart_select()


        binding.btnPlay.setOnClickListener {
            //data.id = movie id
            viewModel.gettrsiler_movie(data.id)
        }
        viewModel.response_toprated.observe(requireActivity(), Observer {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(constants.youtubel_link + it))
            startActivity(intent)
        })



        return binding.root
    }

    fun append_genre() {
        hashMap.put(28, "Action")
        hashMap.put(12, "Adventure")
        hashMap.put(16, "Animation")
        hashMap.put(35, "Comedy")
        hashMap.put(80, "Crime")
        hashMap.put(99, "Documentary")
        hashMap.put(18, "Drama")
        hashMap.put(10751, "Family")
        hashMap.put(14, "Fantasy")
        hashMap.put(36, "History")
        hashMap.put(27, "Horror")
        hashMap.put(10402, "Music")
        hashMap.put(9648, "Mystery")
        hashMap.put(10749, "Romance")
        hashMap.put(878, "Science Fiction")
        hashMap.put(10770, "TV Movie")
        hashMap.put(53, "Thriller")
        hashMap.put(10752, "War")
        hashMap.put(37, "Western")
        for (id in data.genreIds!!) {
            for (key in hashMap.keys) {
                if (key == id) {
                    binding.txtGenreDetailed.append(hashMap[key] + "  ")
                }
            }

        }
    }

    fun image_heart_select() {
        binding.imageViewHeart.setOnClickListener {
            if (binding.imageViewAnimation.isSelected) {
                binding.imageViewAnimation.isSelected = false
            } else {
                binding.imageViewAnimation.setSelected(true)
                binding.imageViewAnimation.likeAnimation()
            }
        }
    }

}