package com.example.assignmentbalusonawane.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.model.Categories

class AdapterCategories(
    val list: MutableList<Categories>,
    private val onclick: (identifier: String) -> Unit
) : RecyclerView.Adapter<AdapterCategories.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val categorie = list[position]

        holder.apply {
            title?.text = categorie.name
        }

        holder.itemView.setOnClickListener {
            onclick(categorie.identifier)
        }

    }

    override fun getItemCount() = list.size

    fun addCategories(categories: List<Categories>){
        this.list.clear()
        this.list.addAll(categories)
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_view_list_default, parent, false)) {

        val title: TextView? = itemView.findViewById(R.id.textViewDefault)
    }

}