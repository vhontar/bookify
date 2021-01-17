package com.vhontar.bookify.aaa.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */

//@BindingAdapter("imageUrl")
//fun ImageView.bindImageUrl(imageView: ImageView, url: String) {
//    Glide.with(context)
//        .load(url)
//        .into(imageView)
//}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}