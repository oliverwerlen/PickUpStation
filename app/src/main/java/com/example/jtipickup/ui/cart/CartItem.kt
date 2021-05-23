package com.example.jtipickup.ui.cart

import com.example.jtipickup.ui.products.ProductItem

data class CartItem(
    val product: ProductItem,
    var amount: Int
)