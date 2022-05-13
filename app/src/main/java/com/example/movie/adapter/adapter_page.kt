package com.example.movie.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import java.util.ArrayList

class adapter_page(var item :ArrayList<Int>): RecyclerView.Adapter<adapter_page.viewholder>() {
    class viewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var page = itemView.findViewById<TextView>(R.id.txtpage)
        fun  bind( page1:Int){
            page.text = page1.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.page_item,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(item[position])

        holder.itemView.setOnClickListener {
            if (onItemClickListener!=null){
                onItemClickListener?.onppageclick(position,item[position])
            }
        }


    }

    override fun getItemCount(): Int {
       return item.size
    }

    interface onpageclickListner{
        fun onppageclick(position: Int ,page: Int)
    }
    var onItemClickListener:onpageclickListner?=null

    fun setonitemclicklistner(onppageclick:onpageclickListner){
        onItemClickListener = onppageclick
    }
}