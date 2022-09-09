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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.paging_adapter
import com.example.movie.databinding.FragmentAllMoviesBinding
import com.example.movie.ui.main.home.AllMoviesFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AllMoviesFragment : Fragment() {

    lateinit var binding: FragmentAllMoviesBinding
    var pagingAdapter: paging_adapter = paging_adapter()
    val viewModel: AllMoviesFragmentViewModel by activityViewModels()
    lateinit var data: String
    lateinit var layoutManager: GridLayoutManager
    var state: Parcelable? = null
    var position = 0
    var positionIndex = 0
    var topView = 0


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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_movies, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val viewVav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        viewVav.visibility = View.GONE


        init_recycler()
        perform_internet()

        binding.shimmerRecyclerSeeall.visibility = View.VISIBLE
        binding.shimmerRecyclerSeeall.startShimmerAnimation()


        if (data == "Top Rated Movies") {

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getListDataTopRated().collect {
                    pagingAdapter.submitData(lifecycle, it)
                }

            }
            binding.recyclerSeeall.adapter = pagingAdapter

        }
        if (data == "Up Coming Movies") {

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.getListDataUpComing().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
            binding.recyclerSeeall.adapter = pagingAdapter


        }
        if (data == "Popular Movies") {

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.getListDataPopular().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
            binding.recyclerSeeall.adapter = pagingAdapter

        }


    }


    fun init_recycler() {
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerSeeall.layoutManager = layoutManager

    }

    fun perform_internet() {
        if (checkForInternet(requireContext())) {
            binding.shimmerRecyclerSeeall.stopShimmerAnimation()
            binding.shimmerRecyclerSeeall.visibility = View.INVISIBLE
            binding.recyclerSeeall.visibility = View.VISIBLE

        } else {
            binding.shimmerRecyclerSeeall.startShimmerAnimation()
            binding.shimmerRecyclerSeeall.visibility = View.VISIBLE
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

    override fun onSaveInstanceState(outState: Bundle) {
        state = binding.recyclerSeeall.layoutManager?.onSaveInstanceState()
        outState.putParcelable("SAVED_RECYCLER_VIEW_STATUS_ID", state)
        super.onSaveInstanceState(outState)
    }

    //save recycler position

    override fun onPause() {
        super.onPause()
        positionIndex = layoutManager.findFirstVisibleItemPosition()
        val startView: View = binding.recyclerSeeall.getChildAt(0)
        topView =
            startView.top - binding.recyclerSeeall.getPaddingTop()

    }

    override fun onResume() {
        super.onResume()
        if (positionIndex != -1) {
            layoutManager.scrollToPositionWithOffset(positionIndex, topView);
        }
    }
}