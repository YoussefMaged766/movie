package com.example.movie.ui.main.Favourite

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.FavouriteAdapter
import com.example.movie.database.Database_viewmodel
import com.example.movie.databinding.FragmentTVBinding
import com.example.movie.models.movie
import com.example.movie.ui.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class favourite_Fragment : Fragment() {

    lateinit var binding: FragmentTVBinding
    lateinit var adapter: FavouriteAdapter
    lateinit var viewmodel: Database_viewmodel
    var movieList :ArrayList<movie> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_t_v_, container, false)
        viewmodel = ViewModelProvider(this).get(Database_viewmodel::class.java)
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.VISIBLE
        adapter = FavouriteAdapter(movieList)
        binding.recyclerFavourite.layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerFavourite.setHasFixedSize(true)
        Log.e("onCreateView: ", adapter.itemCount.toString())

        setHasOptionsMenu(true)

        viewmodel.getAllFavoriteMovies()?.observe(requireActivity(), Observer {
            if (it?.isEmpty() == true) {
                binding.imgError.visibility = View.VISIBLE
                binding.imgError.setAnimation(R.raw.not_found)
                binding.imgError.playAnimation()
                binding.recyclerFavourite.visibility = View.INVISIBLE
            } else {
                adapter.getdata(it as ArrayList<movie>)
                binding.recyclerFavourite.adapter = adapter
                movieList.clear()
                movieList.addAll(it)

            }
        })


//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                adapter.filter.filter(newText)
//                return false
//            }
//
//        })

        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.search_menu, menu)
//        val item = menu.findItem(R.id.searchView_MenuMain)
//        val searchView = SearchView(
//            (context as MainActivity).supportActionBar!!.themedContext
//        )
//        item.actionView = searchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Log.e("onQueryTextSubmit1: ", query.toString())
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
//                return false
//            }
//
//        })
//
//
//    }


}