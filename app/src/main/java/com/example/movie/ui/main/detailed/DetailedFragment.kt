package com.example.movie.ui.main.detailed

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.adapter.adapter_recommended
import com.example.movie.database.Database_viewmodel
import com.example.movie.databinding.FragmentDetailedBinding
import com.example.movie.models.ResultsItem1
import com.example.movie.models.movie
import com.example.movie.ui.main.home.homefragment_viewmodel
import com.example.movie.util.CenterZoomLayoutManager
import com.example.movie.util.constants


class detailedFragment : Fragment() {

    lateinit var binding: FragmentDetailedBinding
    lateinit var viewModel: movedetaild_viewmodel
    lateinit var viewmodel2: homefragment_viewmodel
    lateinit var databaseViewmodel: Database_viewmodel
    var data: movie? = null
    lateinit var adapter: adapter_recommended
    lateinit var array: ArrayList<ResultsItem1>
    var hashMap: HashMap<Int, String> = HashMap()
    lateinit var mPrefs: SharedPreferences
    var favourite_movie: ArrayList<movie> = ArrayList()
     var like:Int?=null


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
        databaseViewmodel = ViewModelProvider(this).get(Database_viewmodel::class.java)
        mPrefs = activity?.getPreferences(MODE_PRIVATE)!!
        var id_select = mPrefs.getString("id", "")
        var img_select = mPrefs.getBoolean("favourite",false)
        if (id_select == data?.id.toString() && (img_select==true) ){
            binding.imageViewAnimation.isSelected = true
        } else {
            binding.imageViewAnimation.isSelected = false
        }


//and in if


        display_movie_detailes()






        append_genre()
        image_heart_select()


        binding.btnPlay.setOnClickListener {
            //data.id = movie id
            viewModel.gettrsiler_movie(data?.id)

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
        viewModel.getrecommended_movie(data?.id)
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

    fun image_heart_select() {
        binding.imageViewHeart.setOnClickListener {
            if (binding.imageViewAnimation.isSelected) {
                if (like==1){
                    binding.imageViewAnimation.isSelected = false
                    databaseViewmodel.UnFavoriteMovie(data?.id)
                    databaseViewmodel.deleteMovie(data)
                    like=0

                }

//                mPrefs.edit().putBoolean("favourite", like).apply()
//                mPrefs.edit().putString("id", data?.id.toString()).apply()
//                if (favourite_movie.isNotEmpty()) {
//                    favourite_movie.remove(data)
//                }
//                var prefsEditor: SharedPreferences.Editor = mPrefs.edit()
//                var gson: Gson = Gson()
//                var json: String = gson.toJson(favourite_movie)
//                prefsEditor.putString("MyObject", json)
//                prefsEditor.apply()
//                Log.e("image_heart_select: ", favourite_movie.toString())

            } else {
                binding.imageViewAnimation.isSelected = true
                binding.imageViewAnimation.likeAnimation()
                databaseViewmodel.insertMovie(data)
                databaseViewmodel.setFavoriteMovie(data?.id)
                like=1

//                mPrefs.edit().putBoolean("favourite", like).apply()
//                mPrefs.edit().putString("id", data?.id.toString()).apply()
//
//
//                //data = movie details
//                var myObject = (data)
//                if (myObject != null) {
//                    favourite_movie.add((myObject))
//                }
//                var prefsEditor: SharedPreferences.Editor = mPrefs.edit()
//                var gson: Gson = Gson()
//                var json: String = gson.toJson(favourite_movie)
//                prefsEditor.putString("MyObject", json)
//                prefsEditor.apply()
//
//                Log.e("image_heart_select: ", favourite_movie.toString())

            }
        }

        data?.id?.let { databaseViewmodel.IsFavorite(it) }?.observe(requireActivity(), Observer {
            if (it!=null){
                like=it
                if (it==1){
                    binding.imageViewAnimation.isSelected = true
                }else{
                    binding.imageViewAnimation.isSelected = false
                }
            }
        })
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