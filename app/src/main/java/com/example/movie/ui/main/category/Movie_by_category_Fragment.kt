package com.example.movie.ui.main.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movie.R
import com.example.movie.adapter.PagingMoviesByCategoryAdapter
import com.example.movie.databinding.FragmentMovieByCategoryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class movie_by_category_Fragment : Fragment() {


    val viewModel: viewmodel by viewModels()
    lateinit var adapter: PagingMoviesByCategoryAdapter
    lateinit var binding: FragmentMovieByCategoryBinding
    var data: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getInt("id")
            Log.e("onCreate: ", data.toString())
        }
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_by_category_,
            container,
            false
        )
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE
        adapter = PagingMoviesByCategoryAdapter()


        return binding.root
    }


    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getListDataCategory(data).collect {
                adapter.submitData(lifecycle, it)
            }

        }
        binding.recyclerCategory.adapter = adapter
    }




}