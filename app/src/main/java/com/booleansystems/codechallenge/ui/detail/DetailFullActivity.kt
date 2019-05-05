package com.booleansystems.codechallenge.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.booleansystems.codechallenge.R
import com.booleansystems.codechallenge.base.BaseActivity
import com.booleansystems.codechallenge.ui.home.mapper.GalleryImage
import com.booleansystems.codechallenge.utils.glide.GlideImageLoader
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_full.*

class DetailFullActivity : BaseActivity() {

    var mSelectedItem: GalleryImage? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_full)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra(ARG_SELECTED_ITEM)) setDetailInformation()
    }

    fun setDetailInformation() {
        mSelectedItem = intent.getParcelableExtra(ARG_SELECTED_ITEM) as GalleryImage
        val options = RequestOptions()
            .centerCrop()
            .priority(Priority.HIGH)
        GlideImageLoader(
            ivDetailTransaction,
            pgAnimationLoadDetail,
            tvPercentageProgressDetail
        ).load(mSelectedItem!!.images!!.firstOrNull()!!.link, options)
        supportActionBar!!.title = mSelectedItem!!.title
    }

    companion object {
        const val ARG_SELECTED_ITEM = "arg_selected_item"
        fun getStartIntent(context: Context, item: GalleryImage): Intent {
            val bundle = Bundle()
            val intent = Intent(context, DetailFullActivity::class.java)
            bundle.putParcelable(ARG_SELECTED_ITEM, item)
            intent.putExtras(bundle)
            return intent
        }
    }
}
