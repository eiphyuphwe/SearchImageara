package com.example.searchimageara.presentation.searchimage

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
import com.example.searchimageara.R
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.presentation.MainActivityDelegate
import com.example.searchimageara.presentation.imagedetial_dialog.ImageDetailViewModel
import com.example.searchimageara.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_search.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ClassCastException

@AndroidEntryPoint
class SearchImageFragment : Fragment(),SearchImageAdapter.OnSearchImageItemClickListener {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar(toolbar = toolbar, R.string.image_search, false)
        mainActivityDelegate.setupNavDrawer(toolbar)
        mainActivityDelegate.enableNavDrawer(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        imageAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(imageData: ImageData) {
        imageData?.let {
            findNavController().navigate(
                R.id.action_searchImageFragment_to_imageDetailDialog2,
                ImageDetailViewModel.createArguments(imageData)
            )
        }
    }

    override fun onUrlCliclk(imageData: ImageData) {
        imageData?.let {
            findNavController().navigate(
                R.id.action_searchImageFragment_to_imageDataWebSite,
                ImageDetailViewModel.createArguments(imageData)
            )
        }
    }
    private fun setupViews() {
        //rvImages.adapter = imageAdapter
        val loaderStateAdapter = LoaderStateAdapter{ imageAdapter.retry() }
        rvImages.adapter = imageAdapter.withLoadStateFooter(loaderStateAdapter)
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

    private fun searchImages(q:String) {
        lifecycleScope.launch {
            viewModel.search(q,  true)
                .collectLatest { pagingData ->
                    imageAdapter.submitData(pagingData)
                }


        }
    }
}