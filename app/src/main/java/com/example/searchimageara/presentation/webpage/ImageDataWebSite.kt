package com.example.searchimageara.presentation.webpage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.searchimageara.R
import com.example.searchimageara.presentation.MainActivityDelegate
import com.example.searchimageara.presentation.searchimage.SearchImageViewModel
import com.example.searchimageara.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_imagedetail.view.*
import kotlinx.android.synthetic.main.fragment_image_search.*
import kotlinx.android.synthetic.main.fragment_imagedata_website.*
import kotlinx.android.synthetic.main.fragment_imagedata_website.view.*
import java.lang.ClassCastException
@AndroidEntryPoint
class ImageDataWebSite : Fragment(){

    private val viewModel: ImageDataWebSiteViewModel by viewModels()
    private lateinit var mainActivityDelegate: MainActivityDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mainActivityDelegate = context as MainActivityDelegate
        } catch (e: ClassCastException) {
            throw ClassCastException()
        }
    }

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
        /*initToolbar(toolbar = toolbar, R.string.image_search, false)
        mainActivityDelegate.setupNavDrawer(toolbar)*/
        mainActivityDelegate.enableNavDrawer(true)
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