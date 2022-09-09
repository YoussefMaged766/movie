package com.example.movie.ui.main.trend

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.ViewPagerAdapter
import com.example.movie.adapter.adapter_trend
import com.example.movie.adapter.adapter_trend_tv
import com.example.movie.databinding.FragmentTrendBinding
import com.example.movie.models.ResultsItem_trendTV
import com.example.movie.models.movie
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator


class trend_Fragment : Fragment() {

    lateinit var binding: FragmentTrendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend_, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val view_nav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view_nav.visibility = View.VISIBLE


        setUpViewPager()


    }

    private fun setUpViewPager() {
        val adapter =
            ViewPagerAdapter(
                supportFragmentManager = requireActivity().supportFragmentManager,
                lifecycle = lifecycle
            )
        adapter.addFragment(TendMovieFragment(), "Movie")
        adapter.addFragment(TrendTVFragment(), "TV")
        binding.viewpager.adapter = adapter
        binding.viewpager.isSaveEnabled = true
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            binding.viewpager.setCurrentItem(tab.position, true)
        }.attach()
    }


}