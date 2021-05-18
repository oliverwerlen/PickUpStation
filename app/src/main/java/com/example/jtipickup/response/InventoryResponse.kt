package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName

data class InventoryResponse(
    @SerializedName("inventory_id")
    val inventory_id: Int
)
