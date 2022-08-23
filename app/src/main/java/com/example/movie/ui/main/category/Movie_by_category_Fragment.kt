package com.example.movie.ui.main.category

import android.os.Bundle
import android.util.Log
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
import com.example.movie.adapter.movie_by_category_adapter
import com.example.movie.databinding.FragmentMovieByCategoryBinding
import com.example.movie.models.movie


class movie_by_category_Fragment : Fragment() {

    lateinit var viewmodel1: viewmodel
    lateinit var adapter: movie_by_category_adapter
    var moive_list = ArrayList<movie>()
    lateinit var binding: FragmentMovieByCategoryBinding
    var data: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getInt("id")
            Log.e( "onCreate: ",data.toString() )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_by_category_,
            container,
            false
        )
        viewmodel1=ViewModelProvider(this).get(viewmodel::class.java)
        viewmodel1.getmovies_by_category(data)
        adapter=movie_by_category_adapter(moive_list)
        viewmodel1.response_category.observe(requireActivity(), Observer {
            adapter.getdata(it as ArrayList<movie>)
        })
        binding.recyclerCategory.adapter=adapter
        viewmodel1.errormassage.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(),it.toString(),Toast.LENGTH_SHORT).show()
        })




        return binding.root
    }


}