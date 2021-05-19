package com.example.jtipickup.ui.products

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.R
import com.example.jtipickup.commons.ViewType
import com.example.jtipickup.commons.ViewTypeDelegateAdapter
import kotlinx.android.synthetic.main.products_item.view.*


class ProductsDelegateAdapter: ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as ProductItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    class TurnsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.products_item)) {
        fun bind(item: ProductItem) = with(itemView) {
            productImg.loadImg(item.image)
            title.text = item.name
            description.text = item.description
            price.text = "Preis: " + item.price
        }
    }
}