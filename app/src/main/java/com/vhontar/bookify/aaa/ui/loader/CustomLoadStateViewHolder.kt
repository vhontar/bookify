package com.vhontar.bookify.aaa.ui.loader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.vhontar.bookify.R
import com.vhontar.bookify.databinding.LoadStateFooterViewItemBinding

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
class CustomLoadStateViewHolder private constructor(
    private val binding: LoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvErrorMsg.text = loadState.error.localizedMessage
        }
        binding.pbLoader.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState !is LoadState.Loading
        binding.tvErrorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CustomLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_view_item, parent, false)
            val binding = LoadStateFooterViewItemBinding.bind(view)
            return CustomLoadStateViewHolder(binding, retry)
        }
    }
}