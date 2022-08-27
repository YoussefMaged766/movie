package com.example.movie.ui.main.toprated

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.adapter.adapter_page
import com.example.movie.adapter.paging_adapter
import com.example.movie.databinding.FragmentTopRatedBinding
import com.example.movie.models.movie
import com.example.movie.ui.main.home.homefragment_viewmodel
import com.example.movie.util.apimanager
import com.example.movie.util.webservices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class top_ratedFragment : Fragment() {

    lateinit var binding: FragmentTopRatedBinding


    var pages = ArrayList<Int>()
    var adapter_page: adapter_page = adapter_page(pages)
     var pagingAdapter: paging_adapter = paging_adapter()
    lateinit var viewModel: homefragment_viewmodel
    lateinit var data: String
    var move_list = ArrayList<movie>()
    lateinit var layoutManager: LinearLayoutManager
    var adater_moviie: adapter = adapter(move_list)
    var last_page_toprated: Int = 1
    var last_page_upcoming = 1
    var last_page_popular = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("movie_type") as String
            Log.e("data", it.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated, container, false)
        viewModel = ViewModelProvider(this).get(homefragment_viewmodel::class.java)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE

//        binding.shimmerRecyclerSeeall.startShimmerAnimation()
//        binding.shimmerRecyclerSeeall.visibility = View.VISIBLE
        layoutManager = GridLayoutManager(requireContext(), 2)
        init_recycler()

        adater_moviie = adapter(move_list)


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getListData().collect {
                pagingAdapter.submitData(it)
            }
        }


        if (data == "Top Rated Movies") {

//            viewModel.response_toprated.observe(requireActivity(), Observer {
//                adater_moviie.getdata(it as ArrayList<movie>)
//            })

            binding.recyclerSeeall.adapter = pagingAdapter
//            pagingAdapter.stateRestorationPolicy= RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

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
        if (data == "Up Coming Movies") {
            viewModel.response_upcoming.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)
                adater_moviie.addlist(it)

            })
            binding.recyclerSeeall.adapter = adater_moviie

//            binding.recyclerSeeall.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                //                var counter = 1
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//
//                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        counter += 1
//                        viewModel.getdatafromapi_upcoming(counter + 1)
//
//                        Log.e("onScrollStateChanged: ", counter.toString())
//                    }
//                }
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val visibleitemcount = layoutManager.childCount
//                    val pastvisibleitem = layoutManager.findFirstVisibleItemPosition()
//                    val total = adater_moviie.itemCount
//                    var page = 1
//                    if (page<200){
//                        if (visibleitemcount+pastvisibleitem>=total)
//                            page++
//                        viewModel.getdatafromapi_upcoming(page)
//
//                    }
//                        super.onScrolled(recyclerView, dx, dy)
//
//
//                }
//            })
            binding.recyclerSeeall.smoothScrollToPosition(adater_moviie.itemCount + 2)
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
        if (data == "Popular Movies") {
            viewModel.response_popular.observe(requireActivity(), Observer {
//                binding.shimmerRecyclerSeeall.stopShimmerAnimation()
//                binding.shimmerRecyclerSeeall.visibility =View.INVISIBLE
                adater_moviie.getdata(it as ArrayList<movie>)

            })
            binding.recyclerSeeall.adapter = adater_moviie
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


    fun init_recycler() {
        binding.apply {
            recyclerSeeall.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerSeeall.layoutManager = layoutManager


            }
        }



    }


    override fun onStart() {
        super.onStart()
        if (checkForInternet(requireContext())) {
//            viewModel.getdatafromapi_toprated(last_page_toprated)
            viewModel.getdatafromapi_upcoming(last_page_upcoming)
            viewModel.getdatafromapi_poppular(last_page_popular)
            binding.shimmerRecyclerSeeall.stopShimmerAnimation()
            binding.shimmerRecyclerSeeall.visibility = View.INVISIBLE
            binding.recyclerSeeall.visibility = View.VISIBLE
            binding.recyclerPages.visibility = View.VISIBLE
        } else {
            binding.shimmerRecyclerSeeall.startShimmerAnimation()
            binding.shimmerRecyclerSeeall.visibility = View.VISIBLE
        }

        Log.e("onStart: ", last_page_upcoming.toString())
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