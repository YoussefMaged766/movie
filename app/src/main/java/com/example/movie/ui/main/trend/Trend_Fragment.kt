package com.example.movie.ui.main.trend

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.adapter_trend
import com.example.movie.adapter.adapter_trend_tv
import com.example.movie.databinding.FragmentTrendBinding
import com.example.movie.models.ResultsItem_trend
import com.example.movie.models.ResultsItem_trendTV
import com.google.android.material.bottomnavigation.BottomNavigationView


class trend_Fragment : Fragment() {

    lateinit var binding: FragmentTrendBinding
    lateinit var viewModel: trend_viewmodel
    lateinit var adapter_trend1: adapter_trend
    lateinit var adpter_trend_tv1 : adapter_trend_tv
    lateinit var layoutManager: GridLayoutManager
    lateinit var layoutManager2: GridLayoutManager
    var array: ArrayList<ResultsItem_trend> = ArrayList()
    var array2 : ArrayList<ResultsItem_trendTV> = ArrayList()
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
        viewModel = ViewModelProvider(this).get(trend_viewmodel::class.java)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.VISIBLE
        val media_type = resources.getStringArray(R.array.media_type)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, media_type
        )
        binding.spinnerMediaType.adapter = adapter

        adapter_trend1 = adapter_trend(array)
        adpter_trend_tv1 = adapter_trend_tv(array2)
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerTrend.layoutManager = layoutManager
        binding.spinnerMediaType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            viewModel.gettrend_movie()
                            binding.recyclerTrend.adapter = adapter_trend1
                            viewModel.response_trend.observe(requireActivity(), Observer {
                                adapter_trend1.getdata(it as ArrayList<ResultsItem_trend>)
                                Log.e( "trend ", it.toString())
                            })

                        }
                        1 -> {
                            viewModel.gettrend_tv()
                            binding.recyclerTrend.adapter = adpter_trend_tv1
                            viewModel.response_trend_tv.observe(requireActivity(), Observer {
                                adpter_trend_tv1.getdata(it as ArrayList<ResultsItem_trendTV>)
                                Log.e( "trend ", it.toString())
                            })
                        }

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }





        binding.recyclerTrend.adapter = adapter_trend1




        return binding.root
    }


}