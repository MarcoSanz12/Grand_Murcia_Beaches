package com.cotesa.murcia.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cotesa.appcore.extension.loadFromUrl
import com.cotesa.appcore.extension.notNull
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.util.Constant
import com.cotesa.common.util.NotificationUtils
import com.cotesa.common.util.SettingsUtils
import com.cotesa.murcia.R
import com.cotesa.murcia.databinding.ItemBeachBinding
import java.util.Collections


class CustomBeachListAdapter(private var beachList: List<Beach>, private val listener: RecyclerViewOnItemClickListener<Beach>) :
    RecyclerView.Adapter<CustomBeachListAdapter.BeachViewHolder>() {

    private val originalList = beachList

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

    fun filterItems(text:String){
        beachList =
            if (text.isNotEmpty())
                originalList.filter {
                    it.title!!.contains(text,true) }
            else
                originalList

        notifyDataSetChanged()

    }

    fun sortItems (comparator:Comparator<Beach>){
        Collections.sort(beachList,comparator)
        notifyDataSetChanged()
    }

    inner class BeachViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        var binding = ItemBeachBinding.bind(view)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun render(beachModel : Beach){

            binding.tvItemName.text = beachModel.title

            // Load image if url not null
            beachModel.mainImage?.get(0)?.url?.notNull {
                binding.ivItemImage.loadFromUrl(it)
            }
            val context = binding.ivItemImage.context

            val prefs = context.getSharedPreferences(Constant.USER_SETTINGS, 0)

            val favBeachesSet = prefs.getStringSet(Constant.USER_SETTINGS_FAV_BEACHES, null) ?: mutableSetOf()

            Log.d("Favorites",favBeachesSet.toString())

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
