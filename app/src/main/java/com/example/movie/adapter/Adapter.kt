package com.example.movie.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.util.constants
import com.example.movie.models.movie


class adapter( var list: List<movie>?) : RecyclerView.Adapter<adapter.viewholder>() {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
        var txtdate = itemView.findViewById<TextView>(R.id.txtreleasedate)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        var item = list?.get(position)
        holder.txtTitle.setText(item?.title)
        holder.txtdate.setText(item?.releaseDate)
        holder.img.clipToOutline = true
        Glide.with(holder.itemView).load(constants.img_link + item?.posterPath).into(holder.img)
        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("movie_details",item)
            it.findNavController().navigate(R.id.action_homeFragment_to_detailedFragment,bundle)

        }

    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    fun getdata(data1: List<movie>){
        list=data1
        notifyDataSetChanged()

    }
}