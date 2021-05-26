package com.example.jtipickup.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.R
import com.example.jtipickup.requests.LoginRequest
import com.example.jtipickup.response.LoginResponse
import com.example.jtipickup.retrofit.ApiClient
import com.example.jtipickup.ui.login.SessionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.products_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode


class CartFragment: Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var cartViewModel: CartViewModel

    var items: List<CartItem> = emptyList<CartItem>()
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)
        sessionManager = SessionManager(requireContext())
        this.v = inflater.inflate(R.layout.fragment_cart, container, false)

        var checkoutButton: Button = v.findViewById<Button>(R.id.checkoutButton)
        if(sessionManager.fetchAuthToken() != null){
           checkoutButton.isEnabled = true
        }
        checkoutButton.setOnClickListener() {
            cartViewModel.createOrder(requireContext())
        }
        cartViewModel.view =  this.v
        cartViewModel.activity = requireActivity()

        return this.v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        items = CartCompanion.getAllCartItems(requireContext())

        cart_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CartAdapter(items) {
                updateTotal()
            }
        }
        cart_list.addItemDecoration(
            DividerItemDecoration(
                context, LinearLayoutManager.VERTICAL
            )
        )
        updateTotal()
    }

    fun updateTotal() {
        var totalPrice = CartCompanion.getAllCartItems(requireContext())
            .fold(0.toDouble()){ acc, cartItem ->
                acc + cartItem.amount.times(cartItem.product.price)
            }
        cartTotalNumber.text = "${BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_EVEN)} CHF"
    }
}