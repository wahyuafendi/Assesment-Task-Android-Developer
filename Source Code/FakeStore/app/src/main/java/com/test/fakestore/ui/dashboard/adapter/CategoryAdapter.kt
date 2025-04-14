package com.test.fakestore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.fakestore.R
import com.test.fakestore.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var items: List<String> = emptyList(),
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedCategory: String? = null

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = items[position]
        val isSelected = category == selectedCategory

        holder.binding.btnCategory.text = category
        holder.binding.btnCategory.setBackgroundResource(
            if (isSelected) R.drawable.bg_category_selected else R.drawable.bg_category_unselected
        )
        holder.binding.btnCategory.setTextColor(
            holder.itemView.context.getColor(
                if (isSelected) android.R.color.white else android.R.color.black
            )
        )

        holder.binding.btnCategory.setOnClickListener {
            selectedCategory = category
            notifyDataSetChanged()
            onClick(category)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateCategories(newCategories: List<String>) {
        items = newCategories
        notifyDataSetChanged()
    }

    fun selectFirstCategory() {
        if (items.isNotEmpty()) {
            selectedCategory = items[0]
            notifyDataSetChanged()
            onClick(selectedCategory!!)
        }
    }
}
