package com.example.jtipickup.ui.products

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.products_item.view.*
import java.lang.reflect.Type

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
                Log.d("ADAPTER", "Button Test")
                addToCart(item, item.id.toString())
            }
        }

        private fun addToCart(item: ProductItem, key: String?) {
            val prefs: SharedPreferences = view.context.getSharedPreferences("cart", Context.MODE_PRIVATE)
            val gson = Gson()
            val editor: SharedPreferences.Editor = prefs.edit()

            val getJson: String? = prefs.getString(key, null)
            if(getJson == null) {
                Log.d("ADAPTER", "No Duplicate found")
                item.amount = 1
                val json: String = gson.toJson(item)
                editor.putString(key, json)
                editor.apply()
            }
            else {
                val type = object : TypeToken<ProductItem>() {}.type
                val newItem: ProductItem = gson.fromJson<ProductItem>(getJson, type)
                newItem.amount += 1
                val json: String = gson.toJson(newItem)
                editor.putString(key, json)
                editor.apply()
                Log.d("ADAPTER", "Duplicate found")
            }
        }
    }
}