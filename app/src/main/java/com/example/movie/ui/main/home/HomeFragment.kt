package com.example.movie.ui.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.adapter.category_adapter
import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.models.movie
import com.example.movie.ui.main.MainActivity
import com.example.movie.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {


    lateinit var adapter_category: category_adapter
    lateinit var adapterMovie: adapter
    lateinit var binding: FragmentHomeBinding
    lateinit var mainViewModel: MainViewModel


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

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(apimanager.getwebbservices())
        ).get(MainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val viewNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        viewNav.visibility = View.VISIBLE
        recycler()
        setHasOptionsMenu(true)

        performSearch()
        if (checkForInternet(requireContext())) {

            binding.shimmerRecycler.stopShimmerAnimation()
            binding.shimmerRecycler2.stopShimmerAnimation()
            binding.shimmerRecycler3.stopShimmerAnimation()
            binding.shimmerRecycler.visibility = View.INVISIBLE
            binding.shimmerRecycler2.visibility = View.INVISIBLE
            binding.shimmerRecycler3.visibility = View.INVISIBLE
            binding.recyclerToprated.visibility = View.VISIBLE
            binding.recyclerPopular.visibility = View.VISIBLE
            binding.recyclerUpcoming.visibility = View.VISIBLE
        }
        else {
            binding.shimmerRecycler.startShimmerAnimation()
            binding.shimmerRecycler.visibility = View.VISIBLE
            binding.shimmerRecycler2.startShimmerAnimation()
            binding.shimmerRecycler2.visibility = View.VISIBLE
            binding.shimmerRecycler3.startShimmerAnimation()
            binding.shimmerRecycler3.visibility = View.VISIBLE
        }


        navigation()
        observation()


    }


    fun recycler() {

        adapterMovie =adapter(arrayListOf())
        val snapHelper = LinearSnapHelper()
        val snapHelper2 = LinearSnapHelper()
        val snapHelper3 = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerToprated)
        snapHelper2.attachToRecyclerView(binding.recyclerUpcoming)
        snapHelper3.attachToRecyclerView(binding.recyclerPopular)
        binding.recyclerToprated.isNestedScrollingEnabled = false
        binding.recyclerUpcoming.isNestedScrollingEnabled = false
        binding.recyclerPopular.isNestedScrollingEnabled = false

        val layoutManager1 = CenterZoomLayoutManager(requireContext())
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        val layoutManager2 = CenterZoomLayoutManager(requireContext())
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        val layoutManager3 = CenterZoomLayoutManager(requireContext())
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        binding.recyclerToprated.layoutManager = layoutManager1

        binding.recyclerUpcoming.layoutManager = layoutManager2

        binding.recyclerPopular.layoutManager = layoutManager3

        adapter_category = category_adapter(constants.category_list)
        binding.recyclerCategory.adapter = adapter_category
    }

    private fun performSearch() {

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val b = Bundle()
                b.putString("search", query.toString())
                findNavController().navigate(R.id.action_nav_home_to_searchFragment, b)
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {

                return true


            }
        })

    }





    fun navigation() {
        val bundle = Bundle()
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
    }

    fun observation() {

        viewLifecycleOwner.lifecycleScope.launch{

            mainViewModel.getTopRatedMovies().observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.recyclerToprated.visibility = View.VISIBLE
                            binding.shimmerRecycler.visibility = View.INVISIBLE
                            binding.shimmerRecycler.stopShimmerAnimation()
                            resource.data.let {response->
                                adapterMovie = adapter(response?.results as ArrayList<movie>?)
                                binding.recyclerToprated.adapter = adapterMovie
                            }
                        }
                        Status.ERROR -> {
                            binding.recyclerToprated.visibility = View.VISIBLE
                            binding.shimmerRecycler.visibility =View.GONE
                            binding.shimmerRecycler.stopShimmerAnimation()
                            Toast.makeText(requireActivity() ,it.message ,Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            binding.shimmerRecycler.visibility = View.VISIBLE
                            binding.shimmerRecycler.startShimmerAnimation()
                            binding.recyclerToprated.visibility= View.INVISIBLE
                        }
                    }
                }
            })
            mainViewModel.getUpComingMovies().observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.recyclerUpcoming.visibility = View.VISIBLE
                            binding.shimmerRecycler2.visibility = View.INVISIBLE
                            binding.shimmerRecycler2.stopShimmerAnimation()
                            resource.data.let {response->
                                adapterMovie = adapter(response?.results as ArrayList<movie>?)
                                binding.recyclerUpcoming.adapter = adapterMovie
                            }
                        }
                        Status.ERROR -> {
                            binding.recyclerUpcoming.visibility = View.VISIBLE
                            binding.shimmerRecycler2.visibility =View.GONE
                            binding.shimmerRecycler2.stopShimmerAnimation()
                            Toast.makeText(requireActivity() ,it.message ,Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            binding.shimmerRecycler2.visibility = View.VISIBLE
                            binding.shimmerRecycler2.startShimmerAnimation()
                            binding.recyclerUpcoming.visibility= View.INVISIBLE
                        }
                    }
                }
            })
            mainViewModel.getPopularMovies().observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.recyclerPopular.visibility = View.VISIBLE
                            binding.shimmerRecycler3.visibility = View.INVISIBLE
                            binding.shimmerRecycler3.stopShimmerAnimation()
                            resource.data.let {response->
                                adapterMovie = adapter(response?.results as ArrayList<movie>?)
                                binding.recyclerPopular.adapter = adapterMovie
                            }
                        }
                        Status.ERROR -> {
                            binding.recyclerPopular.visibility = View.VISIBLE
                            binding.shimmerRecycler3.visibility =View.GONE
                            binding.shimmerRecycler3.stopShimmerAnimation()
                            Toast.makeText(requireActivity() ,it.message ,Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {
                            binding.shimmerRecycler3.visibility = View.VISIBLE
                            binding.shimmerRecycler3.startShimmerAnimation()
                            binding.recyclerPopular.visibility= View.INVISIBLE
                        }
                    }
                }
            })
        }

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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        menu.clear()
//        inflater.inflate(R.menu.search_menu, menu)
//        var item = menu.findItem(R.id.searchView_MenuMain)
//        val searchView = SearchView(
//            (context as MainActivity).supportActionBar!!.themedContext
//        )
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Log.e("onQueryTextSubmit1: ", query.toString())
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                return true
//            }
//
//        })
//
//
//    }


}

















