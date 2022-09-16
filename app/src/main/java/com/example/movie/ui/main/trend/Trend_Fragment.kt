package com.example.movie.ui.main.trend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movie.R
import com.example.movie.adapter.ViewPagerAdapter
import com.example.movie.databinding.FragmentTrendBinding
import com.example.movie.util.FadeOutTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator


class trend_Fragment : Fragment() {

    lateinit var binding: FragmentTrendBinding
    var cubeOutTransformation= FadeOutTransformation()

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
        binding.viewpager.setPageTransformer(cubeOutTransformation)
    }


}