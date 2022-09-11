package com.example.movie.ui.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movie.R
import com.example.movie.adapter.PagingSearchAdapter
import com.example.movie.databinding.FragmentSearchBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

lateinit var  binding :FragmentSearchBinding

var adapter :PagingSearchAdapter = PagingSearchAdapter()
    lateinit var viewmodel: ViewModel
    lateinit var data :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        data = it.getString("search").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        viewmodel = ViewModelProvider(this).get(ViewModel::class.java)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.GONE

         return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewmodel.getListDataSerch(data).collect {
                adapter.submitData(lifecycle, it)
            }

        }

        binding.recyclerSearch.adapter=adapter
    }


}