package com.cotesa.murcia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.util.NotificationUtils
import com.cotesa.common.util.SettingsUtils
import com.cotesa.murcia.R
import com.cotesa.murcia.databinding.ItemBeachBinding


class CustomBeachListAdapter(private val beachList: List<Beach>, private val listener: RecyclerViewOnItemClickListener<Beach>) :
    RecyclerView.Adapter<CustomBeachListAdapter.BeachViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeachViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_beach,parent,false)
        return BeachViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeachViewHolder, position: Int) {
        val item = beachList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return beachList.size
    }


    inner class BeachViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        var binding = ItemBeachBinding.bind(view)

        fun render(beachModel : Beach){

            binding.tvItemName.text = beachModel.title

            var url = beachModel.mainImage?.get(0)?.url
            val context = binding.ivItemImage.context

            Glide.with(context).load(url).into(binding.ivItemImage)

            if (SettingsUtils.isInFavorites(context,beachModel)){
                binding.clItemLayout.background = context.getDrawable(R.color.favorite_color_transparent)
            }
            // XD
            else{
                binding.clItemLayout.background = context.getDrawable(R.color.transparent)
            }

        }


        override fun onClick(p0: View?) {
            listener.onItemClick(p0,beachList[bindingAdapterPosition])

        }


        init {
            itemView.setOnLongClickListener(this)
            itemView.setOnClickListener(this)

        }

        override fun onLongClick(p0: View?): Boolean {
            listener.onItemLongClick(p0,beachList[bindingAdapterPosition],bindingAdapterPosition)
            return true
        }
    }
}

interface RecyclerViewOnItemClickListener<T> {
    fun onItemClick(v: View?, model: T)
    fun onItemLongClick(v:View?, model:T, position: Int)
}
