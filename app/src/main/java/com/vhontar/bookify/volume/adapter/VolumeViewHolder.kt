package com.vhontar.bookify.volume.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.databinding.AdapterVolumeItemBinding

/**
 * Created by Vladyslav Hontar (vhontar) on 30.01.21.
 */
class VolumeViewHolder private constructor(private val binding: AdapterVolumeItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(volume: Volume) {
        binding.apply {
            it = volume
            executePendingBindings()
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