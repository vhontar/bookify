package com.vhontar.bookify.volume

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.databinding.AdapterVolumeItemBinding

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumeAdapter: PagingDataAdapter<Volume, VolumeViewHolder>(VolumeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VolumeViewHolder.from(parent)
    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

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

private class VolumeDiffCallback : DiffUtil.ItemCallback<Volume>() {
    override fun areItemsTheSame(oldItem: Volume, newItem: Volume): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Volume, newItem: Volume): Boolean {
        return oldItem == newItem
    }
}
