package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.models.ResultsItem_trendTV
import com.example.movie.models.movie
import com.example.movie.util.constants
import java.util.*
import kotlin.collections.ArrayList

class PagingTrendTVAdapter : PagingDataAdapter<ResultsItem_trendTV, PagingTrendTVAdapter.viewholder>(MovieDiffCallBack2()) {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
        var txtdate = itemView.findViewById<TextView>(R.id.txtreleasedate)

        fun bind(data: ResultsItem_trendTV) {
            txtTitle.text = data.name
            txtdate.text = data.firstAirDate
            Glide.with(itemView).load(constants.img_link + data.posterPath).into(img)
        }
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(getItem(position)!!)
        Log.e( "onBindViewHolder: ",position.toString() )
        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("tv_details", getItem(position))
            bundle.putInt("TVID", getItem(position)?.id!!)
            bundle.putIntegerArrayList("genres" ,getItem(position)?.genreIds)
            it.findNavController().navigate(R.id.action_nav_trend_to_trend_tvFragment, bundle)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem, parent, false)
        return viewholder(view)
    }




}

class MovieDiffCallBack2 : DiffUtil.ItemCallback<ResultsItem_trendTV>() {
    override fun areItemsTheSame(oldItem: ResultsItem_trendTV, newItem: ResultsItem_trendTV): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultsItem_trendTV, newItem: ResultsItem_trendTV): Boolean {
        return oldItem == newItem
    }


}