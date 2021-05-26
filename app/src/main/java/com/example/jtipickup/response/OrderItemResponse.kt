package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName

data class OrderItemResponse(
    @SerializedName("orderItem_id")
    var orderItem_id: Int,
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("productCode")
    var productCode: String
)
