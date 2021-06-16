/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.searchimageara.presentation.searchimage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                Glide.with(itemView.context).load(thumbnail).into(ivImageCover)
                tvTitle.text = title
                tvWebPageUrl.text = webpageUrl
            }

        }
    }

    interface OnSearchImageItemClickListener{
        fun onItemClick(imageData: ImageData)
        fun onUrlCliclk(imageData: ImageData)
    }
}