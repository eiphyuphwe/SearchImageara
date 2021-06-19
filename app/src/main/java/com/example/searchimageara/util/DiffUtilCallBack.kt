package com.example.searchimageara.util

import androidx.recyclerview.widget.DiffUtil
import com.example.searchimageara.domain.model.ImageData


class DiffUtilCallBack : DiffUtil.ItemCallback<ImageData>() {
    override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
        return oldItem.url == newItem.url
                && oldItem.title == newItem.title
                && oldItem.webpageUrl == newItem.webpageUrl
    }
}