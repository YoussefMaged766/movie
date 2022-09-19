package com.example.movie

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.movie.adapter.CrewAdapter
import com.example.movie.adapter.EpisodeAdapter
import com.example.movie.databinding.FragmentTVSeasonsDetailsBinding
import com.example.movie.models.EpisodesItem
import com.example.movie.ui.main.detailed.tv.TVViewModel
import com.example.movie.ui.main.detailed.tv.ViewModelFactoryTV
import com.example.movie.util.CenterZoomLayoutManager
import com.example.movie.util.Status
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import kotlinx.coroutines.launch

class TVSeasonsDetailsFragment : Fragment() {

    lateinit var binding: FragmentTVSeasonsDetailsBinding
    lateinit var adaptercrew: CrewAdapter
    lateinit var adapterEpisode: EpisodeAdapter
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

        val layoutManager1 = CenterZoomLayoutManager(requireContext())
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerEpisode.layoutManager = layoutManager1
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerEpisode)

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
                            if (it.data?.overview!!.isEmpty()){
                                binding.text.visibility=View.INVISIBLE
                            }
                            binding.txtOverview.text = it.data?.overview


                            adaptercrew = CrewAdapter(it.data?.episodes?.get(0)?.crew!!)
                            binding.recyclerCrew.adapter=adaptercrew

                            adapterEpisode = EpisodeAdapter(it.data.episodes as List<EpisodesItem>)
                            binding.recyclerEpisode.adapter=adapterEpisode



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

            viewmodel.getSeasonTrailer(tvid,seasonNumber).observe(requireActivity(), Observer {
                it.let {
                    when(it.status){
                        Status.SUCCESS->{
                            binding.btnPlay.setOnClickListener { view->
                                if (it.data?.results!!.isEmpty()){
                                    Toast.makeText(requireActivity(),"There Is No Trailer for this",Toast.LENGTH_SHORT).show()
                                } else{
                                    val bundle=Bundle()
                                    bundle.putString("key", it.data.results[0]?.key)
                                    view.findNavController().navigate(R.id.action_TVSeasonsDetailsFragment_to_webViewFragment,bundle)
                                }

                            }
                        }
                        Status.LOADING->{}
                        Status.ERROR->{}
                    }
                }
            })
        }



    }


}