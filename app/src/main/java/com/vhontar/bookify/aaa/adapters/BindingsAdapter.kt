package com.vhontar.bookify.aaa.adapters

import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.vhontar.bookify.R

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String) {
    if (Patterns.WEB_URL.matcher(url).matches()) {
        Picasso.get()
            .load(url)
            .into(this)
    } else {
        this.setImageResource(R.drawable.default_book_cover)
    }
}

@BindingAdapter("isGone")
fun View.bindIsGone(isGone: Boolean) {
    this.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}