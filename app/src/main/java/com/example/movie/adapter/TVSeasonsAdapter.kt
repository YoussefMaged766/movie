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
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.models.SeasonsItem
import com.example.movie.models.TVDetailesResponse
import com.example.movie.util.constants

class TVSeasonsAdapter(var tvId: Int?, var items: List<SeasonsItem?>) :
    RecyclerView.Adapter<TVSeasonsAdapter.viewholder>() {



    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgNotFound = itemView.findViewById<ImageView>(R.id.imgNotFound)

        val img = itemView.findViewById<ImageView>(R.id.TVImg)
        val title = itemView.findViewById<TextView>(R.id.txttitle)
        val date = itemView.findViewById<TextView>(R.id.txtdate)
        fun bind(data: SeasonsItem) {
            title.text = data.name
            date.text = data.airDate.toString()
            if (data.posterPath==null){
                imgNotFound.visibility=View.VISIBLE
            }
            Glide.with(itemView).load(constants.img_link + data.posterPath)
                .into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tv_seasonitem, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position]!!)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("seasonNumber", items[position]?.seasonNumber!!)
            bundle.putInt("tvid", tvId!!)
            it.findNavController()
                .navigate(R.id.action_trend_tvFragment_to_TVSeasonsDetailsFragment, bundle)
            Log.e( "onBindViewHolder: ",tvId.toString() )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}