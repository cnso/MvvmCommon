package org.jash.common.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader

@BindingAdapter("image_url")
fun loadImage(image:ImageView, url:String?) {
    Glide.with(image)
        .load(url)
        .into(image)
}
@BindingAdapter("images")
fun loadImages(banner: Banner, images:List<String>?) {
    banner.setImageLoader(object : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            imageView?.let {
                Glide.with(it)
                    .load(path)
                    .into(it)
            }
        }
    })
    images?.let { banner.update(it) }
}