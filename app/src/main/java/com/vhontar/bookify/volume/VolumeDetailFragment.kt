package com.vhontar.bookify.volume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.vhontar.bookify.aaa.viewmodels.volume.networkdatabase.VolumeNetworkWithDatabaseCacheViewModel
import com.vhontar.bookify.aaa.viewmodels.volume.networkonly.VolumeNetworkOnlyViewModel
import com.vhontar.bookify.databinding.FragmentVolumeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@ExperimentalPagingApi
@AndroidEntryPoint
class VolumeDetailFragment: Fragment() {
    private lateinit var viewDataBinding: FragmentVolumeDetailBinding
    private val networkOnlyViewModel: VolumeNetworkOnlyViewModel by viewModels()
    private val networkWithDatabaseCacheViewModel: VolumeNetworkWithDatabaseCacheViewModel by viewModels()
    private val args: VolumeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentVolumeDetailBinding.inflate(inflater, container, false)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = networkOnlyViewModel

        with(viewDataBinding) {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            if (networkOnlyViewModel.isNetworkOnly()) {
                networkOnlyViewModel.getVolume(args.volumeId)
            } else {
                networkWithDatabaseCacheViewModel.getVolume(args.volumeId)
            }
        }
    }
}