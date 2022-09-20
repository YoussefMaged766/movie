package com.example.movie.ui.main

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.adapter.CrewAdapter
import com.example.movie.databinding.FragmentTVEpisodesDetailsBinding
import com.example.movie.ui.main.detailed.tv.TVViewModel
import com.example.movie.ui.main.detailed.tv.ViewModelFactoryTV
import com.example.movie.util.Status
import com.example.movie.util.apimanager
import com.example.movie.util.constants
import kotlinx.coroutines.launch


class TVEpisodesDetailsFragment : Fragment() {

    lateinit var binding: FragmentTVEpisodesDetailsBinding
    var idTv: Int = 0
    var episode_number: Int = 0
    var seasonNumber: Int = 0
    lateinit var viewmodel: TVViewModel
    lateinit var adapter :CrewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idTv = it.getInt("id")
            seasonNumber = it.getInt("seasonNumber")
            episode_number = it.getInt("episodesNumber")
            Log.e( "onCreate:id ",idTv.toString() )
            Log.e( "onCreate:season ",seasonNumber.toString() )
            Log.e( "onCreate:Epsiodes ",episode_number.toString() )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_t_v_episodes_details,
            container,
            false
        )
        viewmodel = ViewModelProvider(
            this,
            ViewModelFactoryTV(apimanager.getwebbservices())
        ).get(TVViewModel::class.java)

        displayData()
        share()

        return binding.root
    }

    fun displayData(){

        viewLifecycleOwner.lifecycleScope.launch {

            viewmodel.getEpisodesDetails(idTv , seasonNumber,episode_number).observe(requireActivity(),
                Observer {
                    it.let {
                        when(it.status){
                            Status.SUCCESS->{
                                if (it.data!!.stillPath ==null){
                                    binding.imgNotFound.visibility = View.VISIBLE
                                }
                               Glide.with(requireActivity()).load(constants.img_link_Episode+ it.data?.stillPath).into(binding.imgDetailedPoster)
                                Glide.with(requireActivity()).load(constants.img_link_Episode+it.data?.stillPath).into(binding.imgDetailed)
                                binding.txtTitleDetailed.text = it.data?.name
                                binding.txtDateDetailed.text = it.data?.airDate
                                binding.txtOverview.text = it.data?.overview
                                if ( it.data?.crew!!.isEmpty()){
                                    binding.txtCrew.visibility = View.GONE
                                }
                                if (it.data.overview!!.isEmpty()){
                                    binding.text.visibility=View.GONE
                                }
                                adapter = CrewAdapter(it.data?.crew!!)
                                binding.recyclerCrew.adapter=adapter

                            }



                            Status.LOADING->{}
                            Status.ERROR->{
                                Log.e( "displayData: ", it.message.toString())
                            }
                        }
                    }

                })


            viewmodel.getEpisodesTrailer(idTv , seasonNumber,episode_number).observe(requireActivity(),
                Observer {
                    it.let {
                        when(it.status){
                            Status.SUCCESS->{
                                binding.btnPlay.setOnClickListener { view->
                                    if (it.data?.results!!.isEmpty()){
                                        Toast.makeText(requireActivity(),"There Is No Trailer for this",Toast.LENGTH_SHORT).show()
                                    } else{
                                        val bundle=Bundle()
                                        bundle.putString("key", it.data.results[0]?.key)
                                        view.findNavController().navigate(R.id.action_TVEpisodesDetailsFragment_to_webViewFragment,bundle)
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

    fun share(){
        binding.imgShare.setOnClickListener { view1 ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewmodel.getEpisodesTrailer(idTv,seasonNumber,episode_number).observe(requireActivity(), Observer {
                    it.let {
                        when (it.status) {
                            Status.SUCCESS -> {
                                if (it.data?.results!![0]?.key ==null){
                                    Toast.makeText(requireActivity() , "There Is No Trailer for this" , Toast.LENGTH_SHORT).show()
                                }

                                val intent= Intent()
                                intent.action= Intent.ACTION_SEND
                                intent.putExtra(Intent.EXTRA_TEXT,constants.youtubel_link+ it.data?.results!![0]?.key)
                                intent.type="text/plain"
                                startActivity(Intent.createChooser(intent,"Share To:"))
                            }
                            Status.LOADING -> {}
                            Status.ERROR -> {}
                        }
                    }
                })
            }

        }
    }


}