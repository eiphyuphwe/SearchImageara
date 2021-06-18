package com.example.searchimageara.presentation.searchimage

import android.graphics.Paint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.searchimageara.R
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.util.DiffUtilCallBack
import kotlinx.android.synthetic.main.item_image.view.*

class SearchImageAdapter :
    PagingDataAdapter<ImageData, SearchImageAdapter.ImageDataViewHolder>(DiffUtilCallBack()) {
    lateinit var onSearchImageSearchImageItemClickListener: OnSearchImageItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageDataViewHolder, position: Int) {
         getItem(position)?.let { holder.bindImageData(it) }
        val item = getItem(position)
         holder.itemView.ivCover.setOnClickListener{
             item?.let { it1 -> onSearchImageSearchImageItemClickListener.onItemClick(it1) }
         }
        holder.itemView.tvWebPageUrl.setOnClickListener{
            item?.let { it1 -> onSearchImageSearchImageItemClickListener.onUrlCliclk(it1) }
        }


    }

    fun setOnItemClickListener(listenerSearchImage:OnSearchImageItemClickListener){
        onSearchImageSearchImageItemClickListener = listenerSearchImage
    }

    class ImageDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImageCover: ImageView = itemView.ivCover
        private val tvTitle: TextView = itemView.tvTitle
        private val tvWebPageUrl: TextView = itemView.tvWebPageUrl

        fun bindImageData(imageData: ImageData) {
            with(imageData) {
                Glide.with(itemView.context).load(thumbnail)
                    .transform( CenterCrop(), RoundedCorners(25))
                    .into(ivImageCover)
                tvTitle.text = title
                tvWebPageUrl.text = webpageUrl
                tvWebPageUrl.paintFlags =  Paint.UNDERLINE_TEXT_FLAG
            }

        }
    }

    interface OnSearchImageItemClickListener{
        fun onItemClick(imageData: ImageData)
        fun onUrlCliclk(imageData: ImageData)
    }
}