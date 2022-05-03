package com.example.movie.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.databinding.FragmentHomeBinding
import com.example.movie.models.movie
import com.example.movie.ui.main.movieviewmodle
import com.example.movie.util.CenterZoomLayoutManager
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var viewModel: homefragment_viewmodel
    var movie = ArrayList<movie>()
    lateinit var adapter: adapter
    lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(homefragment_viewmodel::class.java)

        recycler()
        viewModel.getdatafromapi(1)

        performSearch()

        viewModel.response1.observe(requireActivity(), Observer {

            adapter.getdata(it as ArrayList<movie>)
            movie.clear()
            movie.addAll(it )


        })




        viewModel.errormassage.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        binding.btngo.setOnClickListener {

            var x = Integer.parseInt(binding.editpage.text.toString())
            viewModel.getdatafromapi(x)

        }


        return binding.root
    }

    fun recycler() {

        adapter = adapter(movie)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.isNestedScrollingEnabled = false

        var layoutManager1 = CenterZoomLayoutManager(requireContext())
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager1

    }

    private fun performSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
//                    search(newText)
//                }
                if (adapter!=null){
                    adapter.filter.filter(newText)
                }

                return false


            }
        })
    }


}

















