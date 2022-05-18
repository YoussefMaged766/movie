package com.example.movie.ui.main.toprated

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.adapter.adapter_page
import com.example.movie.databinding.FragmentTopRatedBinding
import com.example.movie.models.movie
import com.example.movie.ui.main.home.homefragment_viewmodel


class top_ratedFragment : Fragment() {

    lateinit var binding: FragmentTopRatedBinding
    lateinit var adater_moviie: adapter
    var pages = ArrayList<Int>()
    var adapter_page: adapter_page = adapter_page(pages)
    lateinit var viewModel: homefragment_viewmodel
    lateinit var data: String
    var move_list = ArrayList<movie>()
    lateinit var layoutManager: LinearLayoutManager

     var last_page_toprated:Int = 1
    var last_page_upcoming=1
    var last_page_popular=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("movie_type") as String
            Log.e( "data",it.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated, container, false)
        viewModel = ViewModelProvider(this).get(homefragment_viewmodel::class.java)
        layoutManager = GridLayoutManager(requireContext(), 2)


        adater_moviie = adapter(move_list)
        if (data == "Top Rated Movies") {
            viewModel.response_toprated.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)

            })
         init_recycler()





            viewModel.pages_toprated.observe(requireActivity(), Observer {
                adapter_page.apply {
                    adapter_page(addpages(it))
                    binding.recyclerPages.adapter = this
                }

            })

            adapter_page.setonitemclicklistner(object : adapter_page.onpageclickListner {
                override fun onppageclick(position: Int, page: Int) {
                    viewModel.getdatafromapi_toprated(page)
                    last_page_toprated = page
                }

            })
//            binding.recyclerPages.adapter = adapter_page
//            Log.e("onCreateView: ",addpages().toString() )
        }
        if (data=="Up Coming Movies"){
            viewModel.response_upcoming.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)

            })
            init_recycler()
            viewModel.pages_upcoming.observe(requireActivity(), Observer {
                adapter_page.apply {
                    adapter_page(addpages(it))
                    binding.recyclerPages.adapter = this
                }

            })
            adapter_page.setonitemclicklistner(object : adapter_page.onpageclickListner {
                override fun onppageclick(position: Int, page: Int) {
                    viewModel.getdatafromapi_upcoming(page)
                    last_page_upcoming = page
                }

            })

        }
        if (data=="Popular Movies"){
            viewModel.response_popular.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)

            })
            init_recycler()
            viewModel.pages_popular.observe(requireActivity(), Observer {
                adapter_page.apply {
                    adapter_page(addpages(it))
                    binding.recyclerPages.adapter = this
                }

            })

            adapter_page.setonitemclicklistner(object : adapter_page.onpageclickListner {
                override fun onppageclick(position: Int, page: Int) {
                    viewModel.getdatafromapi_poppular(page)
                    last_page_popular = page
                }

            })
        }





        return binding.root
    }

    fun addpages(page: Int): ArrayList<Int> {

        for (i in 1..page) {
            pages.add(i)
        }

        return pages


    }
    fun init_recycler(){
        adater_moviie = adapter(move_list)
        binding.recyclerSeeall.layoutManager = layoutManager
        binding.recyclerSeeall.adapter = adater_moviie
    }

    override fun onStart() {
        super.onStart()
        if(checkForInternet(requireContext())){
            viewModel.getdatafromapi_toprated(last_page_toprated)
            viewModel.getdatafromapi_upcoming(last_page_upcoming)
            viewModel.getdatafromapi_poppular(last_page_popular)
            binding.shimmerRecyclerSeeall.stopShimmerAnimation()
            binding.shimmerRecyclerSeeall.visibility =View.INVISIBLE
            binding.recyclerSeeall.visibility = View.VISIBLE
            binding.recyclerPages.visibility =View.VISIBLE
        }
        else{
            binding.shimmerRecyclerSeeall.startShimmerAnimation()
            binding.shimmerRecyclerSeeall.visibility = View.VISIBLE
        }

        Log.e( "onStart: ", last_page_upcoming.toString())
    }
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

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
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }



}