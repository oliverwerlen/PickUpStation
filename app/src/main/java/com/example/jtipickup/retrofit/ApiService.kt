package com.example.jtipickup.retrofit

import com.example.jtipickup.requests.LoginRequest
import com.example.jtipickup.response.InventoryResponse
import com.example.jtipickup.response.LoginResponse
import com.example.jtipickup.response.PickUpResponse
import com.example.jtipickup.response.ProductsResponse
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
    fun getPickUps(): Call<List<PickUpResponse>>

    @GET(Constants.INVENTORY_BY_PICKUP_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getInventoriesByPickUp(): Call<InventoryResponse>

    @GET(Constants.PRODUCT_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getProducts(): Call<List<ProductsResponse>>
}