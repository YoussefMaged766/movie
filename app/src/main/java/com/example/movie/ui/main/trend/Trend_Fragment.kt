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
import com.example.movie.adapter.adapter_trend
import com.example.movie.adapter.adapter_trend_tv
import com.example.movie.databinding.FragmentTrendBinding
import com.example.movie.models.ResultsItem_trendTV
import com.example.movie.models.movie
import com.google.android.material.bottomnavigation.BottomNavigationView


class trend_Fragment : Fragment() {

    lateinit var binding: FragmentTrendBinding

    //    lateinit var viewModel: trend_viewmodel
    val viewModel: trend_viewmodel by activityViewModels()

    lateinit var layoutManager: GridLayoutManager
    var array: ArrayList<movie> = ArrayList()
    var array2: ArrayList<ResultsItem_trendTV> = ArrayList()

    var adapter_trend1: adapter_trend = adapter_trend(array)
    var adpter_trend_tv1: adapter_trend_tv = adapter_trend_tv(array2)

    var mListState: Parcelable? = null
    var savedInstanceState = Bundle()
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
//        viewModel = ViewModelProvider(this).get(trend_viewmodel::class.java)
//        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        view.visibility = View.VISIBLE
//        val media_type = resources.getStringArray(R.array.media_type)
//
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_item, media_type
//        )
//        binding.spinnerMediaType.adapter = adapter
//
//
//
//
//        layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.recyclerTrend.layoutManager = layoutManager
//
//
////        viewModel.gettrend_movie()
//        viewModel.gettrend_tv()
//        binding.spinnerMediaType.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    when (position) {
//                        0 -> {
//
//
////                            viewModel.response_trend.observe(requireActivity(), Observer {
////
////                                adapter_trend1 = adapter_trend(array)
////                                adapter_trend1.getdata(it as ArrayList<movie>)
////                                binding.recyclerTrend.adapter = adapter_trend1
//////
//////                                Log.e( "trend ", it.toString())
////                            })
//
//                            if (savedInstanceState == null) {
//
//
//                                viewModel.getmoviesdata()?.observe(requireActivity(), Observer {
//                                    adapter_trend1 = adapter_trend(array)
//                                    adapter_trend1.getdata(it as ArrayList<movie>)
//                                    binding.recyclerTrend.adapter = adapter_trend1
//                                    Log.e("onItemSelected: ", it.toString())
//                                })
//                            }
//                        }
//
//                        1 -> {
//
//
//                            viewModel.response_trend_tv.observe(requireActivity(), Observer {
//                                adpter_trend_tv1 = adapter_trend_tv(array2)
//                                adpter_trend_tv1.getdata(it as ArrayList<ResultsItem_trendTV>)
//                                binding.recyclerTrend.adapter = adpter_trend_tv1
//                                Log.e("trend ", it.toString())
//                            })
//                        }
//
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                }
//
//            }
//
//
//
//
//
//
//        Log.e("onViewStateRestored: ", mListState.toString())



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this).get(trend_viewmodel::class.java)
        val view_nav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view_nav.visibility = View.VISIBLE

        val media_type = resources.getStringArray(R.array.media_type)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, media_type
        )
        binding.spinnerMediaType.adapter = adapter




        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerTrend.layoutManager = layoutManager


//        viewModel.gettrend_movie()
        viewModel.gettrend_tv()
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

                            viewModel.getmoviesdata()?.observe(requireActivity(), Observer {
                                    adapter_trend1 = adapter_trend(array)
                                    adapter_trend1.getdata(it as ArrayList<movie>)
                                    binding.recyclerTrend.adapter = adapter_trend1
                                    Log.e("onItemSelected: ", it.toString())
                                })
//                            viewModel.response_trend.observe(requireActivity(), Observer {
//
//                                adapter_trend1 = adapter_trend(array)
//                                adapter_trend1.getdata(it as ArrayList<movie>)
//                                binding.recyclerTrend.adapter = adapter_trend1
////
//                                Log.e( "trend ", it.toString())
//                            })

//                            if (savedInstanceState == null) {
//
//
//
//                            }
                        }

                        1 -> {


                            viewModel.response_trend_tv.observe(requireActivity(), Observer {
                                adpter_trend_tv1 = adapter_trend_tv(array2)
                                adpter_trend_tv1.getdata(it as ArrayList<ResultsItem_trendTV>)
                                binding.recyclerTrend.adapter = adpter_trend_tv1
                                Log.e("trend ", it.toString())
                            })
                        }

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }






        Log.e("onViewStateRestored: ", mListState.toString())




    }
    override fun onStop() {
        super.onStop()


        val mListState = binding.recyclerTrend.layoutManager?.onSaveInstanceState()
        savedInstanceState.putParcelable("LIST_STATE_KEY", mListState)
    }

    override fun onResume() {
        super.onResume()

        val mListState: Parcelable? = savedInstanceState.getParcelable("LIST_STATE_KEY")
        binding.recyclerTrend.layoutManager?.onRestoreInstanceState(mListState)

    }


}