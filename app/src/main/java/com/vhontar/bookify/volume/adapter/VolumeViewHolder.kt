package com.vhontar.bookify.volume.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vhontar.bookify.R
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.databinding.AdapterVolumeItemBinding
import com.vhontar.bookify.volume.PopularVolumesFragmentDirections
import com.vhontar.bookify.volume.ViewAllVolumesFragmentDirections

/**
 * Created by Vladyslav Hontar (vhontar) on 30.01.21.
 */
class VolumeViewHolder private constructor(private val binding: AdapterVolumeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(volume: Volume) {
        binding.apply {
            it = volume
            executePendingBindings()

            clBookRoot.setOnClickListener {
                it.findNavController().navigate(
                    if (it.findNavController().currentDestination?.id == R.id.viewAllVolumesFragment) {
                        ViewAllVolumesFragmentDirections.actionViewAllVolumesFragmentToVolumeDetailFragment(
                            volume.id
                        )
                    } else {
                        PopularVolumesFragmentDirections.actionVolumesFragmentToVolumeDetailFragment(
                            volume.id
                        )
                    }
                )
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): VolumeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AdapterVolumeItemBinding.inflate(layoutInflater, parent, false)

            return VolumeViewHolder(binding)
        }
    }
}