package com.example.plantproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantproject.R
import com.example.plantproject.model.Product

data class Product(val _id: String, val image: String, val name: String, val price: Double, val detail: String)

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val productNameView: TextView = itemView.findViewById(R.id.productName)
        val productPriceView: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false) // Ensure the layout is correct
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productNameView.text = product.name
        holder.productPriceView.text = product.price.toString()
        Glide.with(holder.imageView.context)
            .load(product.image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = products.size
}
