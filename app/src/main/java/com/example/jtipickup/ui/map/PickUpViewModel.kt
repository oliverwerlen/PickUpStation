package com.example.jtipickup.ui.map

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jtipickup.response.LoginResponse
import com.example.jtipickup.response.PickUpResponse
import com.example.jtipickup.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpViewModel: ViewModel(){

    private var apiClient: ApiClient = ApiClient()
    private val TAG = "PickUp"
    var pickUps: MutableLiveData<List<PickUpResponse>> =  MutableLiveData()

    fun getAllPickUps(context: Context){
        val call = apiClient.getApiService(context).getPickUps()
            .enqueue(object : Callback<List<PickUpResponse>> {
                override fun onFailure(call: Call<List<PickUpResponse>>, t: Throwable) {
                    Log.v(TAG, t.toString())
                }
                override fun onResponse(
                    call: Call<List<PickUpResponse>>,
                    response: Response<List<PickUpResponse>>
                ) {
                    val pickUpsResponse = response.body()
                    Log.v(TAG, response.code().toString())
                    if (response.code() == 200 && pickUpsResponse != null) {
                        pickUps.value = pickUpsResponse
                        Log.v(TAG, pickUpsResponse.toString())
                    } else {
                        Toast.makeText(context, "wrong", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

}
