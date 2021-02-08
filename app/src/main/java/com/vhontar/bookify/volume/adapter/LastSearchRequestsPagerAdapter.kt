package com.vhontar.bookify.volume.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.databinding.PageradapterLastSearchRequestItemBinding

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
class LastSearchRequestsPagerAdapter(
    private val context: Context,
    private var searchRequests: List<SearchRequest>
): PagerAdapter() {
    private var currentSearchRequestSelected: SearchRequest? = null

    init {
        if (searchRequests.isNotEmpty())
            currentSearchRequestSelected = searchRequests[0]
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`
    override fun getCount(): Int = searchRequests.size
    override fun getPageWidth(position: Int): Float = if (searchRequests.size > 1) 0.90f else 1f
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = PageradapterLastSearchRequestItemBinding.inflate(inflater, container, false)
        with(viewDataBinding) {
            it = searchRequests[position]
            llRunSearch.setOnClickListener {
                currentSearchRequestSelected = searchRequests[position]
            }
        }

        container.addView(viewDataBinding.root)
        return viewDataBinding.root
    }
    fun currentSearchRequestQuery(): String = currentSearchRequestSelected?.qSearch ?: ""
    fun setItems(items: List<SearchRequest>) {
        if (items.isNotEmpty()) {
            currentSearchRequestSelected = items[0]
        }

        searchRequests = items
        notifyDataSetChanged()
    }
}