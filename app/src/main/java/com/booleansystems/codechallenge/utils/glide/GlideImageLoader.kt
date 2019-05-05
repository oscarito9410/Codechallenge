package com.booleansystems.codechallenge.utils.glide

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Created by oscar on 04/05/19
 * operez@na-at.com.mx
 */
class GlideImageLoader(
    private val mImageView: ImageView?,
    private val mProgressBar: View?,
    private val mPercentageView: TextView?,
    private val mImageReadyListener: ImageReadyListener? = null
) {
    interface ImageReadyListener {
        fun onFinished()
    }


    fun load(url: String?, options: RequestOptions?) {
        if (url == null || options == null) return

        onConnecting()

        //set Listener & start
        ProgressAppGlideModule.expect(url, object : ProgressAppGlideModule.UIonProgressListener {
            override fun onProgress(bytesRead: Long, expectedLength: Long) {
                if (mProgressBar != null) {
                    val percantage = (100 * bytesRead / expectedLength).toInt()
                    if (mPercentageView != null)
                        mPercentageView.text = "$percantage %"
                }
            }

            override fun getGranualityPercentage(): Float {
                return 1.0f
            }
        })
        //Get Image
        Glide.with(mImageView!!.context)
            .load(url)
            .transition(withCrossFade())
            .apply(options.skipMemoryCache(true))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    ProgressAppGlideModule.forget(url)
                    onFinished()
                    if (mImageReadyListener != null)
                        mImageReadyListener!!.onFinished()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    ProgressAppGlideModule.forget(url)
                    onFinished()
                    if (mImageReadyListener != null)
                        mImageReadyListener!!.onFinished()
                    return false
                }

            })
            .into(mImageView)
    }


    private fun onConnecting() {
        if (mProgressBar != null) mProgressBar.visibility = View.VISIBLE
    }

    private fun onFinished() {
        if (mProgressBar != null && mImageView != null && mPercentageView != null) {
            mProgressBar.visibility = View.GONE
            mPercentageView.visibility = View.GONE
            mImageView.visibility = View.VISIBLE
        }
    }
}