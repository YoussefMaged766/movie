package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R

import com.example.movie.util.constants
import com.example.movie.models.movie
import java.util.*
import kotlin.collections.ArrayList
import androidx.navigation.fragment.NavHostFragment


class adapter_recommended(var list: ArrayList<movie>?) :
    RecyclerView.Adapter<adapter_recommended.viewholder>() {
    private val searchList = ArrayList<movie>(list)

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
        holder.setIsRecyclable(false)
        holder.img.clipToOutline = true

        Glide.with(holder.itemView).load(constants.img_link + item?.posterPath).into(holder.img)


        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("movie_details", item)

            it.findNavController().navigate(R.id.action_nav_detailed_self, bundle)


        }


    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun getdata(data1: ArrayList<movie>) {
        list = data1
        notifyDataSetChanged()

    }




}