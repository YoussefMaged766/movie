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
//setHasOptionsMenu(true)
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
//        binding.txtSearch.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun afterTextChanged(editable: Editable) {
//                //after the change calling the method and passing the search input
//                filter1(editable.toString())
//            }
//        })

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val search = menu.findItem(R.id.searchView_MenuMain)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search.."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (adapter!= null){
                    adapter.filter.filter(newText)
                }

                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun filter1(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList <movie> ()
        //looping through existing elements and adding the element to filtered list
        movie.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it.title?.lowercase(Locale.getDefault())?.contains(text.lowercase(Locale.getDefault())) == true
        }
        //calling a method of the adapter class and passing the filtered list

        adapter.filterList(filteredNames)
        Log.e( "filter1: ",filteredNames.toString() )
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
//
//                if (newText!!.isNotEmpty()){
//                    matchedmovie.clear()
//                    val searchtext= newText.lowercase(Locale.getDefault())
//                    movie.forEach {
//                        if (it.title!!.lowercase(Locale.getDefault()).contains(searchtext)){
//                            matchedmovie.add(it)
//                            Log.e("onQueryTextChange: ",matchedmovie.toString() )
//                        }
//
//                    }
//                    binding.recycler.adapter!!.notifyDataSetChanged()
//
//                }else{
//                    matchedmovie.clear()
//                  matchedmovie.addAll(movie)
//                    binding.recycler.adapter!!.notifyDataSetChanged()
//                }

            }
        })
    }

    //    private fun search(text: String?) {
//        matchedmovie = arrayListOf()
//
//        text?.let {
//            movie?.forEach { movie ->
//                if (movie.title?.contains(text, true) == true
//
//                ) {
//                    matchedmovie.add(movie)
//                }
//            }
//            updateRecyclerView()
//            if (matchedmovie.isEmpty()) {
//                Toast.makeText(requireContext(), "No match found!", Toast.LENGTH_SHORT).show()
//            }
//            updateRecyclerView()
//        }
//    }
//    private fun updateRecyclerView() {
//        binding.recycler.apply {
////            adapter.list = matchedmovie
//
//            adapter?.notifyDataSetChanged()
//        }
//    }


}

















