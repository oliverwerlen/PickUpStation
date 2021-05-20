package com.example.jtipickup.ui.products

import com.example.jtipickup.commons.AdapterConstants
import com.example.jtipickup.commons.ViewType

data class ProductItem(
    val id: Int,
    val productCode: String?,
    val name: String,
    val description: String,
    val price: Double,
    val image: String
) : ViewType {
    override fun getViewType() = AdapterConstants.PRODUCTS
}
