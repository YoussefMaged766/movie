package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.models.EpisodesItem
import com.example.movie.models.SeasonsItem
import com.example.movie.models.TVSeasonsDetailsResponse
import com.example.movie.util.constants

class EpisodeAdapter(var tvId :Int, var seasonNumber:Int ,var items:List<EpisodesItem>):RecyclerView.Adapter<EpisodeAdapter.viewholder>() {

    class viewholder(itemView: View) : ViewHolder(itemView){
        var imgNotFound = itemView.findViewById<ImageView>(R.id.imgNotFound)

        val img = itemView.findViewById<ImageView>(R.id.TVImg)
        val title = itemView.findViewById<TextView>(R.id.txttitle)
        val date = itemView.findViewById<TextView>(R.id.txtdate)
        fun bind(data: EpisodesItem) {
            title.text = data.name
            date.text = data.airDate.toString()
            if (data.stillPath==null){
                imgNotFound.visibility=View.VISIBLE
            }
            Glide.with(itemView).load(constants.img_link_Episode + data.stillPath)
                .into(img)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.tv_seasonitem,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id" , tvId)
            bundle.putInt("seasonNumber" , seasonNumber)
            bundle.putInt("episodesNumber" , items[position].episodeNumber!!)
            it.findNavController().navigate(R.id.action_TVSeasonsDetailsFragment_to_TVEpisodesDetailsFragment,bundle)
            Log.e( "onBindViewHolderid: ",tvId.toString() + "season  ${seasonNumber}  +  eposides ${items[position].episodeNumber!!}" )

        }
    }

    override fun getItemCount(): Int {
        return  items.size
    }
}