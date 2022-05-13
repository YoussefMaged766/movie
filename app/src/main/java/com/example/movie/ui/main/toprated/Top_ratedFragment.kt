package com.example.movie.ui.main.toprated

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.adapter.adapter
import com.example.movie.adapter.adapter_page
import com.example.movie.databinding.FragmentTopRatedBinding
import com.example.movie.models.movie
import com.example.movie.ui.main.home.homefragment_viewmodel


class top_ratedFragment : Fragment() {

    lateinit var binding: FragmentTopRatedBinding
    lateinit var adater_moviie: adapter
    var pages = ArrayList<Int>()
    var adapter_page: adapter_page = adapter_page(pages)
    lateinit var viewModel: homefragment_viewmodel
    lateinit var data: String
    var move_list = ArrayList<movie>()
    lateinit var layoutManager: LinearLayoutManager
     var last_page_toprated:Int = 1
    var last_page_upcoming=1
    var last_page_popular=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("movie_type") as String
            Log.e( "data",it.toString())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated, container, false)
        viewModel = ViewModelProvider(this).get(homefragment_viewmodel::class.java)
        layoutManager = GridLayoutManager(requireContext(), 2)
        adater_moviie = adapter(move_list)
        if (data == "Top Rated Movies") {
            viewModel.response_toprated.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)

            })
         init_recycler()



            viewModel.pages_toprated.observe(requireActivity(), Observer {
                adapter_page.apply {
                    adapter_page(addpages(it))
                    binding.recyclerPages.adapter = this
                }

            })

            adapter_page.setonitemclicklistner(object : adapter_page.onpageclickListner {
                override fun onppageclick(position: Int, page: Int) {
                    viewModel.getdatafromapi_toprated(page)
                    last_page_toprated = page
                }

            })
//            binding.recyclerPages.adapter = adapter_page
//            Log.e("onCreateView: ",addpages().toString() )
        }
        if (data=="Up Coming Movies"){
            viewModel.response_upcoming.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)

            })
            init_recycler()
            viewModel.pages_upcoming.observe(requireActivity(), Observer {
                adapter_page.apply {
                    adapter_page(addpages(it))
                    binding.recyclerPages.adapter = this
                }

            })
            adapter_page.setonitemclicklistner(object : adapter_page.onpageclickListner {
                override fun onppageclick(position: Int, page: Int) {
                    viewModel.getdatafromapi_upcoming(page)
                    last_page_upcoming = page
                }

            })

        }
        if (data=="Popular Movies"){
            viewModel.response_popular.observe(requireActivity(), Observer {
                adater_moviie.getdata(it as ArrayList<movie>)

            })
            init_recycler()
            viewModel.pages_popular.observe(requireActivity(), Observer {
                adapter_page.apply {
                    adapter_page(addpages(it))
                    binding.recyclerPages.adapter = this
                }

            })

            adapter_page.setonitemclicklistner(object : adapter_page.onpageclickListner {
                override fun onppageclick(position: Int, page: Int) {
                    viewModel.getdatafromapi_poppular(page)
                    last_page_popular = page
                }

            })
        }




        return binding.root
    }

    fun addpages(page: Int): ArrayList<Int> {

        for (i in 1..page) {
            pages.add(i)
        }

        return pages


    }
    fun init_recycler(){
        adater_moviie = adapter(move_list)
        binding.recyclerSeeall.layoutManager = layoutManager
        binding.recyclerSeeall.adapter = adater_moviie
    }

    override fun onStart() {
        super.onStart()
        viewModel.getdatafromapi_toprated(last_page_toprated)
        viewModel.getdatafromapi_upcoming(last_page_upcoming)
        viewModel.getdatafromapi_poppular(last_page_popular)
        Log.e( "onStart: ", last_page_upcoming.toString())
    }



}