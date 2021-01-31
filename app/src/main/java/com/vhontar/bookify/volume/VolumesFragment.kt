package com.vhontar.bookify.volume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vhontar.bookify.R
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.aaa.viewmodels.VolumeViewModel
import com.vhontar.bookify.databinding.FragmentVolumesBinding
import com.vhontar.bookify.volume.adapter.LastSearchRequestsPagerAdapter
import com.vhontar.bookify.volume.adapter.VolumeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@AndroidEntryPoint
class VolumesFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentVolumesBinding
    private val viewModel: VolumeViewModel by viewModels()

    private val adapter = VolumeAdapter()

    private var searchJob: Job? = null

    private val last4SearchRequests = arrayListOf(
        SearchRequest("Android Development", 343, R.drawable.cover_background_1),
        SearchRequest("Russian Classics", 504, R.drawable.cover_background_2),
        SearchRequest("English Classics", 203, R.drawable.cover_background_3),
        SearchRequest("Christian Literature", 432, R.drawable.cover_background_4)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentVolumesBinding.inflate(inflater, container, false)
        with(viewDataBinding) {
            rvVolumes.adapter = adapter
            vpBookShelves.adapter = LastSearchRequestsPagerAdapter(requireContext(), last4SearchRequests)
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        search("Android")
    }

    private fun updateVolumeListFromInput() {
        // TODO
//        binding.searchRepo.text.trim().let {
//            if (it.isNotEmpty()) {
//                binding.list.scrollToPosition(0)
//                search(it.toString())
//            }
//        }
    }

    private fun initSearch(query: String) {
//        viewDataBinding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_GO) {
//                updateVolumeListFromInput()
//                true
//            } else {
//                false
//            }
//        }
//        viewDataBinding.searchRepo.setOnKeyListener { _, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                updateVolumeListFromInput()
//                true
//            } else {
//                false
//            }
//        }
//
//        // Scroll to top when the list is refreshed from network.
//        lifecycleScope.launch {
//            adapter.loadStateFlow
//                // Only emit when REFRESH LoadState for RemoteMediator changes.
//                .distinctUntilChangedBy { it.refresh }
//                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
//                .filter { it.refresh is LoadState.NotLoading }
//                .collect { viewDataBinding.list.scrollToPosition(0) }
//        }
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getVolumes(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}