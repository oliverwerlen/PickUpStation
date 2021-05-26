package com.example.jtipickup.ui.cart

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.jtipickup.MainActivity
import com.example.jtipickup.R
import com.example.jtipickup.requests.OrderItemRequest
import com.example.jtipickup.requests.OrderRequest
import com.example.jtipickup.response.OrderItemResponse
import com.example.jtipickup.response.OrderResponse
import com.example.jtipickup.retrofit.ApiClient
import com.example.jtipickup.ui.login.SessionManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel: ViewModel() {

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    private lateinit var context: Context
    lateinit var view: View
    lateinit var activity: FragmentActivity

    private val TAG = "SHOPPINGCART"

    fun createOrder(context: Context) {
        this.context = context
        apiClient = ApiClient()
        sessionManager = SessionManager(context)
        apiClient.getApiService(this.context).createOrder(
            OrderRequest(
                sessionManager.fetchPickUpSelected()!!.pickUpStation_id
            )
        )
            .enqueue(object : Callback<OrderResponse> {
                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Log.v(TAG, t.toString())
                }

                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    val orderResponse = response.body()
                    Log.v(TAG, response.code().toString())
                    if (response.code() == 200 && orderResponse != null) {
                        Log.v(TAG, "Judihui order Created")
                        var cartItems: List<CartItem> = CartCompanion.getAllCartItems(context)
                        cartItems.forEach { saveItem(orderResponse.order_id, it) }
                    } else {
                        Log.v(TAG, response.code().toString())
                    }
                }
            })
    }
    fun saveItem(order_id: Int, orderItem: CartItem){
        Log.v(TAG, orderItem.product.productCode.toString())
        apiClient.getApiService(context).createOrderItem(
            OrderItemRequest(
                quantity = orderItem.amount,
                productCode = orderItem.product.productCode,
                order_id = order_id
            )
        )
            .enqueue(object : Callback<OrderItemResponse> {
                override fun onFailure(call: Call<OrderItemResponse>, t: Throwable) {
                    Log.v(TAG, t.toString())
                }

                override fun onResponse(
                    call: Call<OrderItemResponse>,
                    response: Response<OrderItemResponse>
                ) {
                    val orderItemResponse = response.body()
                    Log.v(TAG, response.code().toString())
                    if (response.code() == 200 && orderItemResponse != null) {
                        val snackbar = Snackbar.make(
                            view.findViewById(R.id.cart) as View,
                            "Order received",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                        CartCompanion.deleteAllCartItems(context)
                        reloadFragment()

                    } else {
                        val snackbar = Snackbar.make(
                            view.findViewById(R.id.cart) as View,
                            "Error while processing",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.show()
                    }
                }
            })
    }

    fun reloadFragment(){

        var fragment: Fragment? = activity.supportFragmentManager.findFragmentByTag("cart")
        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        ft.detach(fragment!!)
        ft.attach(fragment)
        ft.commit()
    }
}