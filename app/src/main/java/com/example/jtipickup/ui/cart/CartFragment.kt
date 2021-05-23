package com.example.jtipickup.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.R
import com.example.jtipickup.ui.login.SessionManager
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_products.*
import java.math.BigDecimal
import java.math.RoundingMode


class CartFragment: Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var cartViewModel: CartViewModel
    var items: List<CartItem> = emptyList<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)
        sessionManager = SessionManager(requireContext())
        return inflater.inflate(R.layout.fragment_cart, container, false)
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
        updateTotal()
    }

    fun updateTotal() {
        var totalPrice = CartCompanion.getAllCartItems(requireContext())
            .fold(0.toDouble()){ acc, cartItem ->
                acc + cartItem.amount.times(cartItem.product.price)
            }
        cartTotalNumber.text = "${BigDecimal(totalPrice).setScale(2, RoundingMode.DOWN)} CHF"
    }
}