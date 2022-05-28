package com.example.movie.ui.main.TV

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.favourite_adapter
import com.example.movie.database.Database_viewmodel
import com.example.movie.databinding.FragmentTVBinding
import com.example.movie.models.movie


class TV_Fragment : Fragment() {

lateinit var binding:FragmentTVBinding
 lateinit var adapter: favourite_adapter
 var mPrefs:SharedPreferences?=null
    lateinit var movielist:ArrayList<movie>
    lateinit var viewmodel:Database_viewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_t_v_, container, false)
        viewmodel = ViewModelProvider(this).get(Database_viewmodel::class.java)
//        mPrefs = activity?.getPreferences(Context.MODE_PRIVATE)!!
//        val gson = Gson()
//        val json: String? = mPrefs?.getString("MyObject", null)
//        val type: Type = object : TypeToken<ArrayList<movie?>?>() {}.type
//         movielist = gson.fromJson(json, type)
//
//
//            if (movielist.isEmpty()){
//
//                movielist=ArrayList()
//                binding.txtNodata.visibility =View.VISIBLE
//
//            }else{
//                binding.txtNodata.visibility =View.GONE
//                adapter=adapter((movielist))
//                binding.recyclerFavourite.adapter=adapter
//            }
        binding.recyclerFavourite.setLayoutManager(GridLayoutManager(activity, 2))
        binding.recyclerFavourite.setHasFixedSize(true)


viewmodel.getAllFavoriteMovies()?.observe(requireActivity(), Observer {
    adapter=favourite_adapter()
    adapter.getdata(it as ArrayList<movie>)
    binding.recyclerFavourite.adapter=adapter

})




        return binding.root
    }


}