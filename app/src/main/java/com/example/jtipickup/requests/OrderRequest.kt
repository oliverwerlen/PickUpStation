package com.example.jtipickup.requests

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("pickUpStation_id")
    var pickUpStation_id: Int
)
