package org.jash.common.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.checkbox.MaterialCheckBox
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
@InverseBindingAdapter(attribute = "checkedState")
fun getCheckedState(checkBox: MaterialCheckBox):Int = checkBox.checkedState
@BindingAdapter("checkedStateAttrChanged")
fun checkedStateChanged(checkBox: MaterialCheckBox, changed: InverseBindingListener) {
    checkBox.addOnCheckedStateChangedListener { _, _ -> changed.onChange() }
}
@BindingAdapter("onSwipe")
fun onSwipe(view: View, swipe:() -> Unit){
    (view.layoutParams as? CoordinatorLayout.LayoutParams)?.let {
        val behavior = SwipeDismissBehavior<View>()
        behavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View?) {
                view?.alpha = 1f
                swipe()
            }

            override fun onDragStateChanged(state: Int) {
            }
        }
        it.behavior = behavior
    }
}