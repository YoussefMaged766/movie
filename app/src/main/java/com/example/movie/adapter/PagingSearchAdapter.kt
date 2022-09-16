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
import com.example.movie.models.movie
import com.example.movie.util.constants
import java.util.*
import kotlin.collections.ArrayList

class PagingSearchAdapter : PagingDataAdapter<movie, PagingSearchAdapter.viewholder>(MovieDiffCallBack4()) {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgNotFound = itemView.findViewById<ImageView>(R.id.imgNotFound)
        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
        var txtdate = itemView.findViewById<TextView>(R.id.txtreleasedate)

        fun bind(data: movie) {
            txtTitle.text = data.title
            txtdate.text = data.releaseDate
            if (data.posterPath==null){
                imgNotFound.visibility=View.VISIBLE
            }
            Glide.with(itemView).load(constants.img_link + data.posterPath).into(img)
        }
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(getItem(position)!!)
        Log.e( "onBindViewHolder: ",position.toString() )
        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("movie_details", getItem(position))
            it.findNavController().navigate(R.id.action_searchFragment_to_nav_detailed, bundle)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem, parent, false)
        return viewholder(view)
    }




}

class MovieDiffCallBack4 : DiffUtil.ItemCallback<movie>() {
    override fun areItemsTheSame(oldItem: movie, newItem: movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: movie, newItem: movie): Boolean {
        return oldItem == newItem
    }


}