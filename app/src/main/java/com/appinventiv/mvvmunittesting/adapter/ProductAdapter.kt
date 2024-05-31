package com.appinventiv.mvvmunittesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appinventiv.mvvmunittesting.R
import com.appinventiv.mvvmunittesting.models.ProductListItem
import com.bumptech.glide.Glide

class ProductAdapter(private val productList : List<ProductListItem>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.title
        Glide.with(holder.productImage.context).load(product.image).into(holder.productImage)
    }

    class ProductViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val productImage = itemView.findViewById<ImageView>(R.id.productImage)
        val productName = itemView.findViewById<TextView>(R.id.productName)
    }

}