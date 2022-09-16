package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.util.constants
import com.example.movie.models.movie
import java.util.*
import kotlin.collections.ArrayList


class adapter(var list: ArrayList<movie>? = null) : RecyclerView.Adapter<adapter.viewholder>() {


    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgNotFound = itemView.findViewById<ImageView>(R.id.imgNotFound)
        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
        var txtdate = itemView.findViewById<TextView>(R.id.txtreleasedate)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        if (list != null) {
            val item = list?.get(position)
            holder.txtTitle.text = item?.title
            holder.txtdate.text = item?.releaseDate
            holder.setIsRecyclable(false)
            holder.img.clipToOutline = true

            if (item?.posterPath==null){
               holder.imgNotFound.visibility=View.VISIBLE
            }
            Glide.with(holder.itemView).load(constants.img_link + item?.posterPath).into(holder.img)

            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("movie_details", item)
                it.findNavController()
                    .navigate(R.id.action_top_ratedFragment_to_nav_detailed, bundle)


            }
        }


    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


}