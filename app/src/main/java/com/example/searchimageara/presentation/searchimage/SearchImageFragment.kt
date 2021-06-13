package com.example.searchimageara.presentation.searchimage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.searchimageara.R
import com.example.searchimageara.presentation.MainActivityDelegate
import com.example.searchimageara.util.initToolbar
import kotlinx.android.synthetic.main.fragment_image_search.*
import java.lang.ClassCastException

class SearchImageFragment : Fragment(){

    private val viewModel: SearchImageViewModel by viewModels()
    private lateinit var  mainActivityDelegate : MainActivityDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mainActivityDelegate = context as MainActivityDelegate
        }catch (e:ClassCastException) {
            throw ClassCastException()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar(toolbar = toolbar,R.string.image_search,false)
        mainActivityDelegate.setupNavDrawer(toolbar)
        mainActivityDelegate.enableNavDrawer(true)
    }
}