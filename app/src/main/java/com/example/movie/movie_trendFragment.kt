package com.example.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie.adapter.adapter_trend
import com.example.movie.databinding.FragmentMovieTrendBinding
import com.example.movie.models.ResultsItem_trend
import com.example.movie.ui.main.detailed.movedetaild_viewmodel
import com.example.movie.ui.main.trend.trend_viewmodel
import com.example.movie.util.constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class movie_trendFragment : Fragment() {

lateinit var  binding:FragmentMovieTrendBinding
     var  array :ArrayList<ResultsItem_trend> = ArrayList()
    lateinit var viewmodel:trend_viewmodel
    lateinit var viewModel: movedetaild_viewmodel
    var hashMap: HashMap<Int, String> = HashMap()
    lateinit var data:ResultsItem_trend
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
       data = it.getSerializable("movie_trend")as ResultsItem_trend
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_trend, container, false)
        viewmodel =   ViewModelProvider(this).get(trend_viewmodel::class.java)
        viewModel = ViewModelProvider(this).get(movedetaild_viewmodel::class.java)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.GONE

        append_genre()
        display_movie_detailes()

        binding.btnPlay.setOnClickListener {
            //data.id = movie id
            viewModel.gettrsiler_movie(data?.id)

        }
        viewModel.response_toprated.observe(requireActivity(), Observer {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(constants.youtubel_link + it))
            startActivity(intent)
        })

        binding.imageViewHeart.setOnClickListener {
            if (binding.imageViewAnimation.isSelected) {
                binding.imageViewAnimation.isSelected = false

            } else {
                binding.imageViewAnimation.isSelected = true
                binding.imageViewAnimation.likeAnimation()
            }
        }
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
        if (data?.genreIds!=null) {
            for (id in data?.genreIds!!) {
                for (key in hashMap.keys) {
                    if (key == id) {
                        binding.txtGenreDetailed.append(hashMap[key] + "  ")
                    }
                }

            }
        }
    }

    fun display_movie_detailes() {
        Glide.with(this).load(constants.img_link + data?.backdropPath).into(binding.imgDetailed)
        Glide.with(this).load(constants.img_link + data?.posterPath).into(binding.imgDetailedPoster)
        binding.txtTitleDetailed.text = data?.title
        binding.txtRating.text = data?.voteAverage.toString()
        binding.txtOverview.text = data?.overview
        if (data?.adult == true) {
            binding.imgAdult.setImageResource(R.drawable.ic_baseline_true_24)
        } else {
            binding.imgAdult.setImageResource(R.drawable.ic_baseline_false_24)
        }
    }
}