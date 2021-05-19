package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName

data class InventoryResponse (
    @SerializedName("inventory_id")
    val inventoryID: Long,
    @SerializedName("quantity")
    val quantity: Long,

    @SerializedName("links")
    val links: Links
)

data class Links (
    @SerializedName("product")
    val product: Link,
    @SerializedName("pickUpStation")
    val pickUpStation: Link
)

data class Link (
    val href: String
)
