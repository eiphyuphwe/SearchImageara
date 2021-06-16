package com.example.searchimageara.presentation.imagedetial_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.searchimageara.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_imagedetail.view.*


@AndroidEntryPoint
class ImageDetailDialog : DialogFragment() {

    private val viewModel: ImageDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_imagedetail, container, false)
        viewModel.loadArguments(arguments)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getImageDetailData().observe(this, Observer {
            Glide.with(view.context)
                .load(it.url)
                .into(view.imgDetail)
        })
    }


}