package com.vhontar.bookify.aaa.ui.loader

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
class CustomLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CustomLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CustomLoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        CustomLoadStateViewHolder.create(parent, retry)

}