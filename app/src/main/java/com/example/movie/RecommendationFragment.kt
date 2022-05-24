package com.example.movie

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.movie.adapter.adapter_recommended
import com.example.movie.databinding.FragmentRecommendationBinding
import com.example.movie.models.ResultsItem1
import com.example.movie.models.movie
import com.example.movie.ui.main.detailed.movedetaild_viewmodel
import com.example.movie.ui.main.home.homefragment_viewmodel
import com.example.movie.util.CenterZoomLayoutManager
import com.example.movie.util.constants
import com.google.gson.Gson


class RecommendationFragment : Fragment() {

lateinit var binding:FragmentRecommendationBinding
    lateinit var viewModel: movedetaild_viewmodel
    lateinit var viewmodel2: homefragment_viewmodel
    var data_recommended: ResultsItem1?=null
    lateinit var adapter: adapter_recommended
    lateinit var array: ArrayList<ResultsItem1>
    var hashMap: HashMap<Int, String> = HashMap()
    lateinit var mPrefs: SharedPreferences
    lateinit var favourite_movie:ArrayList<Any>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data_recommended = it.getSerializable("movie_details1") as ResultsItem1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_recommendation, container, false)
        viewModel = ViewModelProvider(this).get(movedetaild_viewmodel::class.java)
        viewmodel2 = ViewModelProvider(this).get(homefragment_viewmodel::class.java)
        mPrefs = activity?.getPreferences(Context.MODE_PRIVATE)!!

        append_genre()
        image_heart_select()
        display_movie_detailes_recommended()
        binding.btnPlay.setOnClickListener {
            //data.id = movie id
            viewModel.gettrsiler_movie(data_recommended?.id)
            viewModel.getrecommended_movie(data_recommended?.id)
        }
        viewModel.response_toprated.observe(requireActivity(), Observer {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(constants.youtubel_link + it))
            startActivity(intent)
        })
        array = ArrayList()
        adapter = adapter_recommended(array)
        var layoutManager1 = CenterZoomLayoutManager(requireContext())
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerRecommendation.layoutManager = layoutManager1
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerRecommendation)
        binding.recyclerRecommendation.adapter = adapter
        viewModel.getrecommended_movie(data_recommended?.id)
        viewModel.response_reccommended.observe(requireActivity(), Observer {
            adapter.getdata(it as ArrayList<ResultsItem1>)
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
        for (id in data_recommended?.genreIds!!) {
            for (key in hashMap.keys) {
                if (key == id) {
                    binding.txtGenreRecommendation.append(hashMap[key] + "  ")
                }
            }

        }
    }

    fun image_heart_select() {
        binding.imageViewHeart.setOnClickListener {
            if (binding.imageViewAnimation.isSelected) {
                binding.imageViewAnimation.isSelected = false

            } else {
                binding.imageViewAnimation.isSelected = true
                binding.imageViewAnimation.likeAnimation()



                favourite_movie=ArrayList()
                //data = movie details
                var   myObject   =  (data_recommended)
                if (myObject != null) {
                    favourite_movie.add((myObject))
                }
                var  prefsEditor : SharedPreferences.Editor = mPrefs.edit()
                var  gson : Gson =  Gson()
                var json :String  = gson.toJson(favourite_movie)
                prefsEditor.putString("MyObject", json)
                prefsEditor.apply()

                Log.e( "image_heart_select: ",favourite_movie.size.toString() )

            }
        }
    }

    fun display_movie_detailes_recommended() {

        Glide.with(this).load(constants.img_link + data_recommended?.backdropPath).into(binding.imgRecommendation)
        Glide.with(this).load(constants.img_link + data_recommended?.posterPath).into(binding.imgRecommendationPoster)
        binding.txtTitleRecommendation.text = data_recommended?.title
        binding.txtRating.text = data_recommended?.voteAverage.toString()
        binding.txtOverview.text = data_recommended?.overview
        if (data_recommended?.adult == true) {
            binding.imgAdult.setImageResource(R.drawable.ic_baseline_true_24)
        } else {
            binding.imgAdult.setImageResource(R.drawable.ic_baseline_false_24)
        }
    }
}