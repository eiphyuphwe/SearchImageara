package com.example.searchimageara.ui.webpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.searchimageara.R
import com.example.searchimageara.ui.MainActivityDelegate
import com.example.searchimageara.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_imagedata_website.*

@AndroidEntryPoint
class ImageDataWebSite : Fragment(){

    private val viewModel: ImageDataWebSiteViewModel by viewModels()
    private lateinit var mainActivityDelegate: MainActivityDelegate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_imagedata_website, container, false)
        viewModel.loadArguments(arguments)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar(toolbar1, 0, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        img_webview.webViewClient = WebViewClient()
        viewModel.getImageDetailData().observe(viewLifecycleOwner, Observer {
            val url = it?.webpageUrl
            url?.let {
                img_webview.loadUrl(url)
            }

        })
    }



}