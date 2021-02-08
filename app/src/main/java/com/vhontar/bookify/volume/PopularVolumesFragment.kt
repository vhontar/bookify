package com.vhontar.bookify.volume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import com.vhontar.bookify.aaa.viewmodels.volume.networkdatabase.VolumeNetworkWithDatabaseCacheViewModel
import com.vhontar.bookify.aaa.viewmodels.volume.networkonly.VolumeNetworkOnlyViewModel
import com.vhontar.bookify.databinding.FragmentPopularVolumesBinding
import com.vhontar.bookify.volume.adapter.LastSearchRequestsPagerAdapter
import com.vhontar.bookify.volume.adapter.PopularVolumeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@ExperimentalPagingApi
@AndroidEntryPoint
class PopularVolumesFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentPopularVolumesBinding
    private val networkOnlyViewModel: VolumeNetworkOnlyViewModel by viewModels()
    private val networkWithDatabaseCacheViewModel: VolumeNetworkWithDatabaseCacheViewModel by viewModels()

    private lateinit var lastSearchRequestsAdapter: LastSearchRequestsPagerAdapter
    private lateinit var volumeAdapter: PopularVolumeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lastSearchRequestsAdapter = LastSearchRequestsPagerAdapter(requireContext(), arrayListOf())
        volumeAdapter = PopularVolumeAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentPopularVolumesBinding.inflate(inflater, container, false)
        with(viewDataBinding) {

            // Bind the footer adapter with the list
            rvVolumes.adapter = volumeAdapter

            vpBookShelves.adapter = lastSearchRequestsAdapter
            tvViewAll.setOnClickListener {
                val currentSearchRequestQuery =
                    (vpBookShelves.adapter as LastSearchRequestsPagerAdapter).currentSearchRequestQuery()

                it.findNavController().navigate(
                    PopularVolumesFragmentDirections.actionPopularVolumesFragmentToViewAllVolumesFragment(
                        currentSearchRequestQuery
                    )
                )
            }
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycleScope.launch {
            val lastSearchRequests = networkWithDatabaseCacheViewModel.getLastSearchRequests()
            lastSearchRequestsAdapter.setItems(lastSearchRequests)
            if (networkOnlyViewModel.isNetworkOnly()) {
                volumeAdapter.setItems(networkOnlyViewModel.getPopularVolumes(lastSearchRequests[0].qSearch))
            } else {
                volumeAdapter.setItems(
                    networkWithDatabaseCacheViewModel.getPopularVolumesForSearchRequest(
                        lastSearchRequests[0]
                    )
                )
            }
        }
    }
}