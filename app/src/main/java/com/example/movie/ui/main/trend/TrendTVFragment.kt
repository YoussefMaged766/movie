package com.example.movie.ui.main.trend

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movie.R
import com.example.movie.adapter.adapter_trend_tv
import com.example.movie.databinding.FragmentTVBinding
import com.example.movie.databinding.FragmentTrendTVBinding
import com.example.movie.models.ResultsItem_trendTV

class TrendTVFragment : Fragment() {

    lateinit var binding: FragmentTrendTVBinding
    lateinit var viewModel: trend_viewmodel
    lateinit var adpter_trend_tv: adapter_trend_tv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend_t_v, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(trend_viewmodel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adpter_trend_tv = adapter_trend_tv(arrayListOf())
        viewModel.tv.observe(requireActivity(), Observer {
            adpter_trend_tv = adapter_trend_tv(it as ArrayList<ResultsItem_trendTV?>)
            binding.recyclerTV.adapter = adpter_trend_tv
            Log.e("trend ", it.toString())
        })


    }


}