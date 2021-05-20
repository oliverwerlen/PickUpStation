package com.example.jtipickup.ui.products

import android.content.Context
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jtipickup.response.PickUpResponse
import com.example.jtipickup.response.ProductsResponse
import com.example.jtipickup.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsViewModel : ViewModel() {

    private var apiClient: ApiClient = ApiClient()
    private val TAG = "Prodcuts"
    var prodcuts: MutableLiveData<List<ProductsResponse>> =  MutableLiveData()

    fun getAllProducts(context: Context){
        val call = apiClient.getApiService(context).getProducts()
            .enqueue(object : Callback<List<ProductsResponse>> {
                override fun onFailure(call: Call<List<ProductsResponse>>, t: Throwable) {
                    Log.v(TAG, t.toString())
                }
                override fun onResponse(
                    call: Call<List<ProductsResponse>>,
                    response: Response<List<ProductsResponse>>
                ) {
                    val productsResponse = response.body()
                    Log.v(TAG, response.code().toString())
                    if (response.code() == 200 && productsResponse != null) {
                        prodcuts.value = productsResponse
                        Log.v(TAG, productsResponse.toString())
                    } else {
                        Toast.makeText(context, "wrong", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}