package com.example.assignmentbalusonawane.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.model.IconSize
import com.example.assignmentbalusonawane.model.Icons

class AdapterIcons(
    private val list: MutableList<Icons>,
    private val onClick: (iconSizes: List<IconSize>) -> Unit
) : RecyclerView.Adapter<AdapterIcons.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val icon = list[position]

        holder.apply {
            premium?.text = if (icon.is_premium) "Premium" else "Free"
            size?.text = icon.raster_sizes[0].size.toString()
            icon_preview?.load(icon.raster_sizes[0].formats[0].preview_url)
        }

        holder.download_button?.setOnClickListener {

            onClick(icon.raster_sizes)

        }

    }

    override fun getItemCount() = list.size

    fun addIcons(icons: List<Icons>){
        this.list.clear()
        this.list.addAll(icons)
        Log.e("A","${list.toString()}")
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_view_icons, parent, false)) {

        val premium: TextView? = itemView.findViewById(R.id.textViewIsPremium)
        val size: TextView? = itemView.findViewById(R.id.textViewIconSize)

        val icon_preview: ImageView? = itemView.findViewById(R.id.imageView11)

        val download_button: Button? = itemView.findViewById(R.id.buttonDownload)

    }
}