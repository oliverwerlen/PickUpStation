package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class OrderResponse(
    @SerializedName("order_id")
    val order_id: Int,
    @SerializedName("pickUpToken")
    val pickUpToken: String
)
