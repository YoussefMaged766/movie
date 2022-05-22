package com.example.movie.ui.main.TV

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.databinding.FragmentTVBinding
import com.example.movie.models.movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TV_Fragment : Fragment() {

lateinit var binding:FragmentTVBinding
lateinit var adapter: adapter
 var mPrefs:SharedPreferences?=null
    lateinit var movielist:ArrayList<movie>
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
        mPrefs = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val gson = Gson()
        val json: String? = mPrefs?.getString("MyObject", null)
        val type: Type = object : TypeToken<ArrayList<movie?>?>() {}.type
         movielist = gson.fromJson(json, type)
        adapter=adapter((movielist))
        binding.recyclerFavourite.adapter=adapter
        try {
            if (movielist==null){

                movielist=ArrayList()
                binding.txtNodata.visibility =View.VISIBLE

            }
        }catch (e:Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
        }




        return binding.root
    }


}