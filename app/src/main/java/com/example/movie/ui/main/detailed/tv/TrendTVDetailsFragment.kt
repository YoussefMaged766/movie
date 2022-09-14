package com.example.movie.ui.main.detailed.tv

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.adapter.TVSeasonsAdapter
import com.example.movie.databinding.FragmentTrendTvDetailedBinding
import com.example.movie.util.Status
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class TrendTVDetailsFragment : Fragment() {

    lateinit var binding: FragmentTrendTvDetailedBinding
    var tvId: Int = 0
    var genres: ArrayList<Int> = ArrayList()
    var hashMap: HashMap<Int, String> = HashMap()
    lateinit var viewmodel: TVViewModel
    lateinit var adapter: TVSeasonsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tvId = it.getInt("TVID")
            genres = it.getIntegerArrayList("genres") as ArrayList<Int>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_trend_tv_detailed, container, false
        )

        viewmodel = ViewModelProvider(
            this,
            ViewModelFactoryTV(apimanager.getwebbservices())
        ).get(TVViewModel::class.java)


        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE
        append_genre()
        display_tv_detailes()
        playTrailer()
        binding.imageViewHeart.setOnClickListener {
            if (binding.imageViewAnimation.isSelected) {
                binding.imageViewAnimation.isSelected = false

            } else {
                binding.imageViewAnimation.isSelected = true
                binding.imageViewAnimation.likeAnimation()
            }
        }

        if (checkForInternet(requireActivity())) {

            binding.shimmerDetailes.visibility = View.GONE
            binding.shimmerDetailes.stopShimmerAnimation()
            binding.container.visibility = View.VISIBLE
        } else {
            binding.container.visibility = View.GONE
            binding.shimmerDetailes.visibility = View.VISIBLE
            binding.shimmerDetailes.startShimmerAnimation()
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
        for (id in genres) {
            for (key in hashMap.keys) {
                if (key == id) {
                    binding.txtGenreDetailed.append(hashMap[key] + "  ")
                }
            }

        }

    }

    fun display_tv_detailes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.getTVDetailes(tvId).observe(requireActivity(), Observer {
                it.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Glide.with(requireActivity())
                                .load(constants.img_link + it.data?.backdropPath)
                                .into(binding.imgDetailed)
                            Glide.with(requireActivity())
                                .load(constants.img_link + it.data?.posterPath)
                                .into(binding.imgDetailedPoster)
                            binding.txtTitleDetailed.text = it.data?.name
                            binding.txtRating.text = it.data?.voteAverage.toString()
                            binding.txtOverview.text = it.data?.overview
                            it.data.let {
                                adapter = TVSeasonsAdapter(it?.id,it?.seasons!!)
                                binding.RecyclerSeasons.adapter=adapter
                            }

                            binding.apply {
                                container.visibility = View.VISIBLE
                                shimmerDetailes.visibility = View.GONE
                                shimmerDetailes.stopShimmerAnimation()
                            }

                        }
                        Status.LOADING -> {
                            binding.apply {
                                container.visibility = View.GONE
                                shimmerDetailes.visibility = View.VISIBLE
                                shimmerDetailes.startShimmerAnimation()
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                requireActivity(),
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.apply {
                                container.visibility = View.VISIBLE
                                shimmerDetailes.visibility = View.GONE
                                shimmerDetailes.stopShimmerAnimation()
                            }
                        }

                    }
                }
            })
        }
    }

    private fun playTrailer() {
        binding.btnPlay.setOnClickListener {view1->
            viewLifecycleOwner.lifecycleScope.launch {
                viewmodel.getTVTrailer(tvId).observe(requireActivity(), Observer {
                    it.let {
                        when (it.status) {
                            Status.SUCCESS -> {
                                if (it.data?.results!!.isEmpty()){
                                    Toast.makeText(requireActivity(),"There Is No Trailer for this",Toast.LENGTH_SHORT).show()

                                } else{
                                    val bundle=Bundle()
                                    bundle.putString("key",it.data?.results?.get(0)?.key)
                                    view1.findNavController().navigate(R.id.action_trend_tvFragment_to_webViewFragment,bundle)
                                    binding.progress.visibility = View.GONE
                                }



                            }
                            Status.LOADING -> {
                                binding.progress.visibility = View.VISIBLE
                                binding.container.isClickable = false

                            }
                            Status.ERROR -> {}
                        }
                    }
                })
            }

        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }
}