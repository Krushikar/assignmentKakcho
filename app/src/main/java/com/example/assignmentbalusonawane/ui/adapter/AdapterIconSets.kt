package com.example.assignmentbalusonawane.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.model.IconSets

class AdapterIconSets(
    private val list: MutableList<IconSets>,
    private val onClick: (id: Int) -> Unit
) : RecyclerView.Adapter<AdapterIconSets.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val iconSets = list[position]

        holder.title?.text = iconSets.name

        holder.itemView.setOnClickListener {

            onClick(iconSets.iconset_id)
        }

    }

    override fun getItemCount() = list.size

    fun addIconSets(sets: List<IconSets>){
        this.list.clear()
        this.list.addAll(sets)
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_view_list_default, parent, false)) {

        val title: TextView? = itemView.findViewById(R.id.textViewDefault)
    }
}