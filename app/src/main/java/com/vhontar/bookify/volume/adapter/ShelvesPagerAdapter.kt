package com.vhontar.bookify.volume.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
class ShelvesPagerAdapter(
    private val shelves:
): PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`
    override fun getCount(): Int = events.size
    override fun getPageWidth(position: Int): Float = if (events.size > 1) 0.93f else 1f
    override fun instantiateItem(container: ViewGroup, position: Int): Any
}