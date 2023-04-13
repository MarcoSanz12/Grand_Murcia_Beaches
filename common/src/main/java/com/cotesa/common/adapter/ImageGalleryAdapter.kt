package com.cotesa.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cotesa.appcore.extension.loadFromUrl
import com.cotesa.appcore.extension.loadFromUrlOverrideParams
import com.cotesa.common.R
import com.cotesa.common.entity.beach.Image


class ImageGalleryAdapter(
    private val context: Context,
    private val items: List<Image>,
    private val listener: RecyclerViewOnItemClickListener<Image>
) :
    RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutForItem: Int = R.layout.item_image_gallery
        val view = inflater.inflate(layoutForItem, viewGroup, false)
        return ViewHolder(context, view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(i)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(var context: Context, itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var ivimage: ImageView = itemView.findViewById(R.id.iv_image)
        fun bind(position: Int) {
            ivimage.loadFromUrl(
                items[position].url!!,
            )
            ivimage.contentDescription = items[position].toString()
        }

        override fun onClick(view: View) {
            listener.onItemClick(view, items[bindingAdapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface RecyclerViewOnItemClickListener<T> {
        fun onItemClick(v: View?, model: T)
    }
}