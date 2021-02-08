package com.vhontar.bookify.volume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.vhontar.bookify.aaa.ui.loader.CustomLoadStateAdapter
import com.vhontar.bookify.aaa.viewmodels.volume.networkdatabase.VolumeNetworkWithDatabaseCacheViewModel
import com.vhontar.bookify.aaa.viewmodels.volume.networkonly.VolumeNetworkOnlyViewModel
import com.vhontar.bookify.databinding.FragmentViewAllVolumesBinding
import com.vhontar.bookify.volume.adapter.VolumePagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Vladyslav Hontar (vhontar) on 08.02.21.
 */
@ExperimentalPagingApi
@AndroidEntryPoint
class ViewAllVolumesFragment: Fragment() {
    private lateinit var viewDataBinding: FragmentViewAllVolumesBinding
    private val networkOnlyViewModel: VolumeNetworkOnlyViewModel by viewModels()
    private val networkWithDatabaseCacheViewModel: VolumeNetworkWithDatabaseCacheViewModel by viewModels()
    private val args: ViewAllVolumesFragmentArgs by navArgs()

    private val adapter = VolumePagingDataAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentViewAllVolumesBinding.inflate(inflater, container, false)
        with(viewDataBinding) {

            // Bind the footer adapter with the list
            rvVolumes.adapter = adapter
                .withLoadStateFooter(
                    footer = CustomLoadStateAdapter { adapter.retry() }
                )
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        load()
    }

    private fun load() {
        lifecycleScope.launch {
            if (networkOnlyViewModel.isNetworkOnly()) {
                networkOnlyViewModel.getVolumes(args.query).collectLatest {
                    adapter.submitData(it)
                }
            } else {
                networkWithDatabaseCacheViewModel.getVolumes(args.query).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}