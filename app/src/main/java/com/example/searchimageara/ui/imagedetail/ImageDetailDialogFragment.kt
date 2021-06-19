package com.example.searchimageara.ui.imagedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.searchimageara.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_imagedetail.view.*


@AndroidEntryPoint
class ImageDetailDialogFragment : DialogFragment() {

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
        viewModel.getImageDetailData().observe(this, {
            it.width?.let { it1 ->
                it.height?.let { it2 ->
                    Glide.with(view.context)
                        .load(it.url)
                        .transform(CenterCrop())
                        .override(it1, it2)
                        .into(view.imgDetail)
                }
            }
        })
    }
}