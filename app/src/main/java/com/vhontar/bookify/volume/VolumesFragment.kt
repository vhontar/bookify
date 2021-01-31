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

    // TODO Temporary provide some search results
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
            tvViewAll.setOnClickListener {  }
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        search("Android")
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