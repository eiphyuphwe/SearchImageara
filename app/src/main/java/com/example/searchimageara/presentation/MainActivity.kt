package com.example.searchimageara.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.searchimageara.R
import com.example.searchimageara.presentation.searchImageList.SearchImageListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: SearchImageListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.search("dogs",1,5,true);
        viewModel.getSearchResults().observe(this, Observer {

            val result = it
            result.forEach {
                println(it.toString())
            }


        })
    }
}