package com.example.demomeow.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demomeow.R
import com.example.demomeow.data.entities.Cat
import kotlinx.android.synthetic.main.item_cat.view.*
import kotlin.properties.Delegates

class CatAdapter(private val onCatClicked: (imageUrl: String) -> Unit) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    // Our data list is going to be notified when we assign a new list of data to it
    private var catsList: List<Cat> by Delegates.observable(emptyList()) {_, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatAdapter.CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_cat, parent, false)
        val holder = CatViewHolder(view)
        // Add the click listener on the imageView and retrieve the proper image url
        holder.itemView.itemCatImageView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onCatClicked.invoke(catsList[holder.adapterPosition].imageUrl)
            }
        }
        return holder
    }

    override fun getItemCount(): Int = catsList.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        // Verify if position exists in list
        if (position != RecyclerView.NO_POSITION) {
            val cat: Cat = catsList[position]
            holder.bind(cat)
        }
    }

    // Update recyclerView's data
    fun updateData(newCatsList: List<Cat>) {
        catsList = newCatsList
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cat: Cat) {
            // Load images using Glide library
            Glide.with(itemView.context)
                .load(cat.imageUrl)
                .centerCrop()
                .thumbnail()
                .into(itemView.itemCatImageView)
        }
    }
}