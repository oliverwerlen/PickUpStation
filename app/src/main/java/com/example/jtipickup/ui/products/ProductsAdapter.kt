package com.example.jtipickup.ui.products

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.MainActivity
import com.example.jtipickup.R
import com.example.jtipickup.ui.cart.CartCompanion
import com.example.jtipickup.ui.cart.CartItem
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.products_item.view.*

class ProductsAdapter(private val prodcutItems: List<ProductItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return prodcutItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.products_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productItem = prodcutItems[position]
        holder as ViewHolder
        holder.bind(productItem)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: ProductItem) = with(itemView) {
            productImg.loadImg(item.image)
            title.text = item.name
            description.text = item.description
            price.text = "Preis: " + item.price + "0 CHF"
            addToCartButton.setOnClickListener{
                CartCompanion.addToCart(
                    CartItem(
                    item,
                    1
                ), item.id.toString(), view.context)

                Snackbar.make(
                    (itemView.context as MainActivity).findViewById(R.id.products_list),
                    "${item.name} wurde zum Warenkorb hinzugefügt",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}