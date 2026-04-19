package com.example.myrecipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.categoryImage)
        val textView: TextView = view.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.textView.text = category.strCategory

        // Load image using Coil
        holder.imageView.load(category.strCategoryThumb) {
//            placeholder(R.drawable.placeholder) // optional placeholder
//            error(R.drawable.error_image)       // optional error image
            crossfade(true)                     // smooth fade-in
        }

        // 🔥 CLICK LISTENER Gesture Detector
        holder.itemView.setOnClickListener {
        }

    }

    override fun getItemCount() = categories.size
}