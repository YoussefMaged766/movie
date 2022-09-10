package com.example.movie.ui.main.trend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movie.R
import com.example.movie.adapter.PagingTrendTVAdapter
import com.example.movie.databinding.FragmentTrendTVBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrendTVFragment : Fragment() {

    lateinit var binding: FragmentTrendTVBinding
    lateinit var viewModel: trend_viewmodel
    lateinit var adpter_trend_tv: PagingTrendTVAdapter
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

        adpter_trend_tv = PagingTrendTVAdapter()


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getListDataTrendTV().collect {
                adpter_trend_tv.submitData(lifecycle, it)
            }

        }
        binding.recyclerTV.adapter = adpter_trend_tv


    }


}