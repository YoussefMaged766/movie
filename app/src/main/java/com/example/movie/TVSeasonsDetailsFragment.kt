package com.example.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movie.adapter.CrewAdapter
import com.example.movie.databinding.FragmentTVSeasonsDetailsBinding
import com.example.movie.ui.main.detailed.tv.TVViewModel
import com.example.movie.ui.main.detailed.tv.ViewModelFactoryTV
import com.example.movie.util.Status
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import kotlinx.coroutines.launch

class TVSeasonsDetailsFragment : Fragment() {

    lateinit var binding: FragmentTVSeasonsDetailsBinding
    lateinit var adaptercrew: CrewAdapter
    lateinit var viewmodel: TVViewModel
    var seasonNumber: Int = 0
    var tvid: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            seasonNumber = it.getInt("seasonNumber")
            tvid = it.getInt("tvid")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_t_v_seasons_details,
            container,
            false
        )
        viewmodel = ViewModelProvider(
            this,
            ViewModelFactoryTV(apimanager.getwebbservices())
        ).get(TVViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.getSeasonDetails(tvid, seasonNumber).observe(requireActivity(), Observer {
                it.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Glide.with(requireActivity())
                                .load(constants.img_link + it.data?.posterPath)
                                .into(binding.imgDetailedPoster)
                            Glide.with(requireActivity())
                                .load(constants.img_link + it.data?.posterPath)
                                .into(binding.imgDetailed)
                            binding.txtTitleDetailed.text = it.data?.name
                            binding.txtDateDetailed.text = it.data?.airDate
                            binding.txtOverview.text = it.data?.overview

                        }
                        Status.LOADING -> {}
                        Status.ERROR -> {
                            Toast.makeText(
                                requireActivity(),
                                it.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            })
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewmodel.getCrewDetails(tvid,seasonNumber).observe(requireActivity(), Observer {
                it.let {
                    when(it.status){
                        Status.SUCCESS->{
//                            adaptercrew = CrewAdapter(listOf(it.data))
//                            binding.recyclerCrew.adapter=adaptercrew
                        }
                        Status.LOADING->{}
                        Status.ERROR->{}

                    }
                }
            })
        }

    }

}