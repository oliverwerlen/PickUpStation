package com.example.jtipickup.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("jwt")
    var jwt: String,

    @SerializedName("id")
    var id: Int,

    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String
)
