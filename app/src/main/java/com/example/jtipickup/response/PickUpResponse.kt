package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class PickUpResponse(
    @SerializedName("pickUpStation_id")
    val pickUpStation_id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("dateAdded")
    val dateAdded: Date,
    @SerializedName("links")
    val links: List<Link>

)

