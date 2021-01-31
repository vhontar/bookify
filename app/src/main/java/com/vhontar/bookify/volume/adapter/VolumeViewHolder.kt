package com.vhontar.bookify.volume.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.databinding.AdapterVolumeItemBinding
import com.vhontar.bookify.volume.VolumesFragmentDirections

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
                    VolumesFragmentDirections.actionVolumesFragmentToVolumeDetailFragment(volume.id)
                )
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): VolumeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AdapterVolumeItemBinding.inflate(layoutInflater, parent, false)

            return VolumeViewHolder(binding)
        }
    }
}