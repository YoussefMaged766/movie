package com.example.movie.ui.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.adapter.category_adapter

import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.models.category_model
import com.example.movie.models.movie
import com.example.movie.ui.main.movieviewmodle
import com.example.movie.util.CenterZoomLayoutManager
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var viewModel: homefragment_viewmodel
    var movie_toprated = ArrayList<movie>()
    var movie_coming = ArrayList<movie>()
    var movie_popular = ArrayList<movie>()
    var category_list = ArrayList<category_model>()
    lateinit var adapter_category: category_adapter
    lateinit var adapter_toprated: adapter
    lateinit var adapter_coming: adapter
    lateinit var adapter_popular: adapter
    lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(homefragment_viewmodel::class.java)

        recycler()
        category_list = arrayListOf(
            category_model("Action", R.drawable.fight_gaming_icon),
            category_model("Action", R.drawable.fight_gaming_icon),
            category_model("Action", R.drawable.fight_gaming_icon),
            category_model("Action", R.drawable.fight_gaming_icon)
        )
        binding.recyclerCategory.adapter = adapter_category

        performSearch()
        if (checkForInternet(requireContext())) {
            viewModel.getdatafromapi_toprated(1)
            viewModel.getdatafromapi_upcoming(1)
            viewModel.getdatafromapi_poppular(1)
            binding.shimmerRecycler.stopShimmerAnimation()
            binding.shimmerRecycler2.stopShimmerAnimation()
            binding.shimmerRecycler3.stopShimmerAnimation()
            binding.shimmerRecycler.visibility = View.INVISIBLE
            binding.shimmerRecycler2.visibility = View.INVISIBLE
            binding.shimmerRecycler3.visibility = View.INVISIBLE
            binding.recyclerToprated.visibility = View.VISIBLE
            binding.recyclerPopular.visibility = View.VISIBLE
            binding.recyclerUpcoming.visibility = View.VISIBLE
        } else {
            binding.shimmerRecycler.startShimmerAnimation()
            binding.shimmerRecycler.visibility = View.VISIBLE
            binding.shimmerRecycler2.startShimmerAnimation()
            binding.shimmerRecycler2.visibility = View.VISIBLE
            binding.shimmerRecycler3.startShimmerAnimation()
            binding.shimmerRecycler3.visibility = View.VISIBLE
        }


        viewModel.response_toprated.observe(requireActivity(), Observer {

            adapter_toprated.getdata(it as ArrayList<movie>)
            movie_toprated.clear()
            movie_toprated.addAll(it)


        })
        viewModel.response_upcoming.observe(requireActivity(), Observer {
            adapter_coming.getdata(it as ArrayList<movie>)
            movie_coming.clear()
            movie_coming.addAll(it)

        })
        viewModel.response_popular.observe(requireActivity(), Observer {
            adapter_popular.getdata(it as ArrayList<movie>)
            movie_popular.clear()
            movie_popular.addAll(it)
        })





        viewModel.errormassage.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            binding.shimmerRecycler.stopShimmerAnimation()
            binding.shimmerRecycler.visibility = View.GONE
        })


        var bundle = Bundle()
        binding.txtAllToprated.setOnClickListener {

            bundle.putString("movie_type", binding.txtToprated.text.toString())
            it.findNavController().navigate(R.id.top_ratedFragment, bundle)
        }
        binding.txtAllUpcoming.setOnClickListener {

            bundle.putString("movie_type", binding.txtUpcoming.text.toString())
            it.findNavController().navigate(R.id.top_ratedFragment, bundle)
        }
        binding.txtAllPopular.setOnClickListener {

            bundle.putString("movie_type", binding.txtPopular.text.toString())
            it.findNavController().navigate(R.id.top_ratedFragment, bundle)
        }


        return binding.root
    }

    fun recycler() {

        adapter_toprated = adapter(movie_toprated)
        adapter_coming = adapter(movie_coming)
        adapter_popular = adapter(movie_popular)
        adapter_category = category_adapter(category_list)
        val snapHelper = LinearSnapHelper()
        val snapHelper2 = LinearSnapHelper()
        val snapHelper3 = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerToprated)
        snapHelper2.attachToRecyclerView(binding.recyclerUpcoming)
        snapHelper3.attachToRecyclerView(binding.recyclerPopular)
        binding.recyclerToprated.isNestedScrollingEnabled = false
        binding.recyclerUpcoming.isNestedScrollingEnabled = false
        binding.recyclerPopular.isNestedScrollingEnabled = false

        var layoutManager1 = CenterZoomLayoutManager(requireContext())
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        var layoutManager2 = CenterZoomLayoutManager(requireContext())
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        var layoutManager3 = CenterZoomLayoutManager(requireContext())
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerToprated.adapter = adapter_toprated
        binding.recyclerToprated.layoutManager = layoutManager1
        binding.recyclerUpcoming.adapter = adapter_coming
        binding.recyclerUpcoming.layoutManager = layoutManager2
        binding.recyclerPopular.adapter = adapter_popular
        binding.recyclerPopular.layoutManager = layoutManager3

    }

    private fun performSearch() {

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {

                adapter_toprated.filter.filter(newText)
                adapter_coming.filter.filter(newText)
                adapter_popular.filter.filter(newText)


                return true


            }
        })

    }

    override fun onResume() {
        super.onResume()
        binding.shimmerRecycler.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerRecycler.stopShimmerAnimation()
        super.onPause()
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

















