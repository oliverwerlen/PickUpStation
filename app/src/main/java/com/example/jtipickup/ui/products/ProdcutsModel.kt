package com.example.jtipickup.ui.products

data class ProductItem(
    val id: Int,
    val productCode: String?,
    val name: String,
    val description: String,
    val price: Double,
    val image: String
)
