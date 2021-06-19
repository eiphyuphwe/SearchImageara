package com.example.searchimageara.ui.searchimage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.searchimageara.R
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.ui.MainActivityDelegate
import com.example.searchimageara.ui.imagedetail.ImageDetailViewModel
import com.example.searchimageara.ui.searchimage.adapters.LoaderStateAdapter
import com.example.searchimageara.ui.searchimage.adapters.SearchImageAdapter
import com.example.searchimageara.ui.webpage.ImageDataWebSiteViewModel
import com.example.searchimageara.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_search.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchImageFragment : Fragment(), SearchImageAdapter.OnSearchImageItemClickListener {

    private val viewModel: SearchImageViewModel by viewModels()
    private lateinit var mainActivityDelegate: MainActivityDelegate
    private val imageAdapter = SearchImageAdapter()

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
        return inflater.inflate(R.layout.fragment_image_search, container, false)
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar(toolbar = toolbar, R.string.app_name, false)
        mainActivityDelegate.setupNavDrawer(toolbar)
        mainActivityDelegate.enableNavDrawer(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        imageAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(imageData: ImageData) {
        imageData.let {
            findNavController().navigate(
                R.id.action_searchImageFragment_to_imageDetailDialog2,
                ImageDetailViewModel.createArguments(imageData)
            )
        }
    }

    override fun onUrlClick(imageData: ImageData) {
        imageData.let {
            findNavController().navigate(
                R.id.action_searchImageFragment_to_imageDataWebSite,
                ImageDataWebSiteViewModel.createArguments(imageData)
            )
        }
    }

    private fun setupViews() {
        //rvImages.adapter = imageAdapter
        val loaderStateAdapter = LoaderStateAdapter {
            imageAdapter.retry()
        }
        rvImages.adapter = imageAdapter.withLoadStateFooter(footer = loaderStateAdapter)
        tvSearch.setOnEditorActionListener { textView, actionId, _ ->

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard(textView)
                    searchImages(textView.text.toString())

                    true
                }
                EditorInfo.IME_ACTION_DONE -> {
                    searchImages(textView.text.toString())
                    true
                }
                else -> false
            }
        }

    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun searchImages(q: String) {

        lifecycleScope.launch {
            viewModel.search(q, true)
                .collectLatest { pagingData ->
                    imageAdapter.submitData(pagingData)
                }


        }

         lifecycleScope.launch {
            imageAdapter.loadStateFlow.collectLatest { loadStates ->
               if(loadStates.refresh is LoadState.Loading){
                   progressBar?.visibility = View.VISIBLE
               }else{
                   progressBar?.visibility = View.GONE
               }
            }
        }
    }
}