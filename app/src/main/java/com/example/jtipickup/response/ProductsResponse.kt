package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class ProductsResponse(
    @SerializedName("product_id")
    val id: Int,
    @SerializedName("prodcut_code")
    val productCode: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("image")
    val image: String
)