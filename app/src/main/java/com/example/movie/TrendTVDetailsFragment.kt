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
import com.example.movie.databinding.FragmentTrendTvDetailedBinding
import com.example.movie.models.ResultsItem_trendTV
import com.example.movie.ui.main.trend.trend_viewmodel
import com.example.movie.util.constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class TrendTVDetailsFragment : Fragment() {

    lateinit var binding: FragmentTrendTvDetailedBinding
    lateinit var data: ResultsItem_trendTV
    var hashMap: HashMap<Int, String> = HashMap()
    lateinit var viewmodel: trend_viewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable("tv_details") as ResultsItem_trendTV
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend_tv_detailed, container, false)
        viewmodel = ViewModelProvider(this).get(trend_viewmodel::class.java)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.GONE
        append_genre()
        display_tv_detailes()

        binding.btnPlay.setOnClickListener {
            viewmodel.gettrailer_tv(data.id)
        }
        viewmodel.response_tv_trailer.observe(requireActivity(), Observer {

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
        hashMap.put(16, "Animation")
        hashMap.put(35, "Comedy")
        hashMap.put(80, "Crime")
        hashMap.put(99, "Documentary")
        hashMap.put(18, "Drama")
        hashMap.put(10751, "Family")
        hashMap.put(9648, "Mystery")
        hashMap.put(10759, "Action & Adventure")
        hashMap.put(10762, "Kids")
        hashMap.put(10763, "News")
        hashMap.put(10764, "Reality")
        hashMap.put(10765, "Sci-Fi & Fantasy")
        hashMap.put(10766, "Soap")
        hashMap.put(10767, "Talk")
        hashMap.put(10768, "War & Politics")
        hashMap.put(37, "Westerns")
        if (data?.genreIds != null) {
            for (id in data?.genreIds!!) {
                for (key in hashMap.keys) {
                    if (key == id) {
                        binding.txtGenreDetailed.append(hashMap[key] + "  ")
                    }
                }

            }
        }

    }

    fun display_tv_detailes() {
        Glide.with(this).load(constants.img_link + data.backdropPath).into(binding.imgDetailed)
        Glide.with(this).load(constants.img_link + data.posterPath).into(binding.imgDetailedPoster)
        binding.txtTitleDetailed.text = data.name
        binding.txtRating.text = data.voteAverage.toString()
        binding.txtOverview.text = data.overview
    }
}