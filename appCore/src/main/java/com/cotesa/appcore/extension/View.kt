package com.cotesa.appcore.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import java.io.File


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun View.generateId(){
    this.id = View.generateViewId()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String) = loadFromUrlLocal(
    this.context.applicationContext,
    if (url.contains("http://")) url.replace("http://", "https://") else url
)
//    Glide.with(this.context.applicationContext)
//        .load(if(url.contains("http://")) url.replace("http://", "https://") else url)
//        .transition(DrawableTransitionOptions.withCrossFade())
//        .into(this)!!

fun ImageView.loadFromUrlLocal(context: Context, url: String) {
    if (url.contains("http://")) url.replace("http://", "https://")
    val file: File =
        File(
            context.getExternalFilesDir(null)
                .toString() + File.separator + (if (url.contains("http://")) url.replace(
                "http://",
                "https://"
            ) else url).substring(
                url.lastIndexOf("/") + 1
            )
        )
    Glide.with(this.context.applicationContext)
        .load(if (file.exists()) file.absolutePath else url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadFromUrlOverrideParams(
    url: String,
    imageWidthPixels: Int,
    imageHeightPixels: Int
) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .override(imageWidthPixels, imageHeightPixels)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(4)))
        .into(this)
fun ImageView.loadFromDrawable(
    drawable: Int
) =
    Glide.with(this.context.applicationContext)
        .load(drawable)
        .into(this)


fun ImageView.loadFromUrlWithError(url: String, idDrawable: Int) {
    val file: File =
        File(
            context.getExternalFilesDir(null)
                .toString() + File.separator + (if (url.contains("http://")) url.replace(
                "http://",
                "https://"
            ) else url).substring(
                url.lastIndexOf("/") + 1
            )
        )
    try {
        val options: RequestOptions = RequestOptions()
            .error(idDrawable)
        Glide.with(this.context.applicationContext)
            .load(
                if (file.exists()) file.absolutePath else if (url.contains("http://")) url.replace(
                    "http://",
                    "https://"
                ) else url
            ).apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    } catch (ex: Exception) {
        Glide.with(this.context.applicationContext)
            .load(idDrawable)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}




val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

