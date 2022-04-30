package com.example.movie.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.*
import com.example.movie.adapter.adapter
import com.example.movie.models.movie
import com.example.movie.util.CenterZoomLayoutManager

class MainActivity : AppCompatActivity() {
    lateinit var adapter: adapter
    lateinit var recycler: RecyclerView
    lateinit var txtpage: EditText
    lateinit var btngo: Button



    lateinit var viewModel: movieviewmodle
    var movie: List<movie>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.recycler)


        txtpage = findViewById<EditText>(R.id.editpage)
        btngo = findViewById<Button>(R.id.btngo)
        viewModel = ViewModelProvider(this).get(movieviewmodle::class.java)

        recycler()
        viewModel.getdatafromapi(1)
        viewModel.response1.observe(this, Observer {
            adapter.getdata(it as List<movie>)
        })

        viewModel.errormassage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        btngo.setOnClickListener {

            var x = Integer.parseInt(txtpage.text.toString())
                viewModel.getdatafromapi(x)

        }

    }


    fun recycler() {

        adapter = adapter(movie)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
        recycler.isNestedScrollingEnabled = false

      var  layoutManager1 = CenterZoomLayoutManager(this)
        layoutManager1.orientation =LinearLayoutManager.HORIZONTAL
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager1

    }
}