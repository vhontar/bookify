package com.vhontar.bookify.volume.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vhontar.bookify.aaa.domain.Volume

/**
 * Created by Vladyslav Hontar (vhontar) on 08.02.21.
 */
class PopularVolumeAdapter(
    private var volumes: List<Volume>
) : RecyclerView.Adapter<VolumeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VolumeViewHolder.create(parent)

    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) =
        holder.bind(volumes[position])

    override fun getItemCount() = volumes.size

    fun setItems(items: List<Volume>) {
        volumes = items
        notifyDataSetChanged()
    }
}