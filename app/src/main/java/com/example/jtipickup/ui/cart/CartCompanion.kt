package com.example.jtipickup.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.os.persistableBundleOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_cart.*

class CartCompanion {


    companion object {
        private val itemType = object : TypeToken<CartItem>() {}.type
        private val gson = Gson()

        fun getAllCartItems(context: Context): List<CartItem> {
            val prefs: SharedPreferences = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE)
            val map: Map<String, *> = prefs.all
            val type = object : TypeToken<CartItem>() {}.type
            var cartItems = mutableListOf<CartItem>()
            map.forEach{ (k, v) ->
                var cartItem: CartItem = gson.fromJson<CartItem>(v.toString(), type)
                cartItems.add(cartItem)
            }
            return cartItems
        }

        fun getCartItem(context: Context, key: String?): CartItem {
            val prefs: SharedPreferences = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE)
            val json: String? = prefs.getString(key, null)
            val item: CartItem = gson.fromJson<CartItem>(json, itemType)
            return item
        }

        fun addToCart(item: CartItem, key: String?, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()

            val getJson: String? = prefs.getString(key, null)
            if(getJson == null) {
                val json: String = gson.toJson(item)
                editor.putString(key, json)
                editor.apply()
            }
            else {
                //val type = object : TypeToken<CartItem>() {}.type
                val newItem: CartItem = gson.fromJson<CartItem>(getJson, itemType)
                newItem.amount += 1
                val json: String = gson.toJson(newItem)
                editor.putString(key, json)
                editor.apply()
            }
        }

        fun removeCartItem(item: CartItem, key: String?, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            var json: String? = prefs.getString(key, null)
            val newItem: CartItem = gson.fromJson<CartItem>(json, itemType)
            if(item.amount > 1) {
                newItem.amount -= 1
                json = gson.toJson(newItem)
                editor.putString(key, json)
                editor.apply()
            }
            else {
                Log.d("CART", "geht nicht")
            }
        }

        fun deleteCartItem(key: String?, context: Context) {
            val prefs: SharedPreferences = context.getSharedPreferences("shoppingCart", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.remove(key)
            editor.apply()
        }

    }
}