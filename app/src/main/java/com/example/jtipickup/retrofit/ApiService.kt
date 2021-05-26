package com.example.jtipickup.retrofit

import com.example.jtipickup.requests.LoginRequest
import com.example.jtipickup.requests.OrderItemRequest
import com.example.jtipickup.requests.OrderRequest
import com.example.jtipickup.response.*
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

    @POST(Constants.ORDER_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun createOrder(@Body request: OrderRequest): Call<OrderResponse>

    @POST(Constants.ORDER_ITEM_URL)
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun createOrderItem(@Body request: OrderItemRequest): Call<OrderItemResponse>

}