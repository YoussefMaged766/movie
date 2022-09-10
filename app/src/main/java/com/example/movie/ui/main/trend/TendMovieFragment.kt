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
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.movie.R
import com.example.movie.adapter.PagingTrendMoviesAdapter
import com.example.movie.adapter.adapter_trend
import com.example.movie.databinding.FragmentTendMovieBinding
import com.example.movie.models.movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TendMovieFragment : Fragment() {

    lateinit var binding: FragmentTendMovieBinding
    lateinit var viewModel: trend_viewmodel
    lateinit var adapter_trend:PagingTrendMoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tend_movie, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(trend_viewmodel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter_trend = PagingTrendMoviesAdapter()


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getListDataTrendMovie().collect {
                adapter_trend.submitData(lifecycle, it)
            }

        }
        binding.recyclerMovie.adapter = adapter_trend

    }


}