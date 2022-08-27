package com.example.movie.ui.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.movie.R
import com.example.movie.adapter.Searchadapter
import com.example.movie.databinding.FragmentSearchBinding
import com.example.movie.models.movie
import com.google.android.material.bottomnavigation.BottomNavigationView


class SearchFragment : Fragment() {

lateinit var  binding :FragmentSearchBinding
var movie_list = ArrayList<movie>()
    lateinit var adapter :Searchadapter
    lateinit var viewmodel1: viewmodel
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
        viewmodel1 = ViewModelProvider(this).get(viewmodel::class.java)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility=View.GONE
        adapter = Searchadapter(movie_list)

        viewmodel1.getsearched_movies(data)
        viewmodel1.response_search.observe(requireActivity(), Observer {
            adapter.getdata(it as ArrayList<movie>)
        })
        viewmodel1.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity() , "Failed to load Data",Toast.LENGTH_SHORT).show()
        })
        binding.recyclerSearch.adapter=adapter




         return  binding.root
    }


}