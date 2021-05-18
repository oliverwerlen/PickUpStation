package com.example.jtipickup.retrofit

import com.example.jtipickup.requests.LoginRequest
import com.example.jtipickup.response.LoginResponse
import com.example.jtipickup.response.PickUpResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for defining REST request functions
 */
interface ApiService {
    @POST(Constants.LOGIN_URL)
    @Headers( "Content-Type: application/json;charset=UTF-8")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET(Constants.PICKUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getPickUps(): Call<PickUpResponse>

    @GET(Constants.INVENTORY_BY_PICKUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getInventoriesByPickUp(): Call<PickUpResponse>
}