package com.booleansystems.codechallenge.ui.home.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.booleansystems.codechallenge.R
import com.booleansystems.codechallenge.ui.home.mapper.GalleryImage
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_result_gallery.view.*

/**

Created by oscar on 04/05/19
operez@na-at.com.mx
 */
class SearchGalleryResultAdapter(
    val mContext: Context,
    val mListResults: MutableList<GalleryImage>
) : RecyclerView.Adapter<SearchGalleryResultAdapter.ViewHolder>() {

    var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(mInflater!!.inflate(R.layout.item_result_gallery, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mContext, mListResults[position])
    }

    override fun getItemCount(): Int {
        return mListResults.size
    }

    fun notifyDataSetChanged(restart: Boolean, list: MutableList<GalleryImage>) {
        if (restart)
            mListResults.clear()
        mListResults.addAll(list)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(context: Context, item: GalleryImage) {
            itemView.tv_title_place.text = item.title!!
            if (item.images != null && item.images.firstOrNull() != null)
                Glide.with(context)
                    .load(item.images.firstOrNull()!!.link)
                    .centerCrop().into(itemView.iv_place)

        }
    }
}