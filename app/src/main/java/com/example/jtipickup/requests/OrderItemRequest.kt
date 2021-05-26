package com.example.jtipickup.requests

import com.google.gson.annotations.SerializedName

data class OrderItemRequest(
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("productCode")
    var productCode: String,
    @SerializedName("order_id")
    var order_id: Int
)
