package com.vhontar.bookify.volume.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vhontar.bookify.aaa.domain.Volume

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumePagingDataAdapter: PagingDataAdapter<Volume, VolumeViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VolumeViewHolder.create(parent)
    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Volume>() {
            override fun areItemsTheSame(oldItem: Volume, newItem: Volume): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Volume, newItem: Volume): Boolean {
                return oldItem == newItem
            }
        }
    }
}
