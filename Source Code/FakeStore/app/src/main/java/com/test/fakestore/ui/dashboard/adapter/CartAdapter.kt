package com.test.fakestore.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fakestore.data.local.model.CartItem
import com.test.fakestore.R

class CartAdapter(
    private var items: List<CartItem>,
    private val onDeleteClick: (CartItem) -> Unit,
    private val onQuantityChange: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val btnIncrease: ImageButton = itemView.findViewById(R.id.btnIncrease)
        val btnDecrease: ImageButton = itemView.findViewById(R.id.btnDecrease)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.txtTitle.text = item.title
        holder.txtPrice.text = "Rp ${item.price}"
        holder.txtQuantity.text = item.quantity.toString()

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.imgProduct)

        holder.btnIncrease.setOnClickListener {
            onQuantityChange(item, item.quantity + 1)
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                onQuantityChange(item, item.quantity - 1)
            }
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
