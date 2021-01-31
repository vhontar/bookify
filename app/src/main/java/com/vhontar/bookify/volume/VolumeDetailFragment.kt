package com.vhontar.bookify.volume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vhontar.bookify.aaa.viewmodels.VolumeViewModel
import com.vhontar.bookify.databinding.FragmentVolumeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@AndroidEntryPoint
class VolumeDetailFragment: Fragment() {
    private lateinit var viewDataBinding: FragmentVolumeDetailBinding
    private val viewModel: VolumeViewModel by viewModels()
    private val args: VolumeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentVolumeDetailBinding.inflate(inflater, container, false)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        with(viewDataBinding) {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            getVolume(args.volumeId)
        }
    }
}