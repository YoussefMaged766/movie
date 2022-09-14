package com.example.movie.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.movie.R
import com.example.movie.databinding.FragmentWebViewBinding
import com.example.movie.models.TrailerResponse
import com.example.movie.util.constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class WebViewFragment : Fragment() {

    lateinit var binding: FragmentWebViewBinding
    lateinit var data: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString("key") .toString()
            Log.e( "onCreate: ",data )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webViewClient= WebViewClient()
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            binding.webView.loadUrl(constants.youtubel_link+data)
        }

        binding.webView.settings.javaScriptEnabled=true

    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }



}