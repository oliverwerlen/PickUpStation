package com.example.jtipickup.retrofit

import com.example.jtipickup.requests.LoginRequest
import com.example.jtipickup.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
/**
 * Interface for defining REST request functions
 */
interface ApiService {
    @POST(Constants.LOGIN_URL)
    @Headers( "Content-Type: application/json;charset=UTF-8")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}