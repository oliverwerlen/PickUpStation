package com.example.jtipickup.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.R
import com.example.jtipickup.ui.products.loadImg
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import kotlinx.android.synthetic.main.cart_item.view.*
import java.math.BigDecimal
import java.math.RoundingMode


class CartAdapter(private var cartItems: List<CartItem>, val itemClick: () -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view, itemClick)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.itemView.deleteCartItem.setOnClickListener {
            CartCompanion.deleteCartItem(cartItem.product.id.toString(), holder.itemView.context)
            refreshView(position, CartCompanion.getAllCartItems(holder.itemView.context))
            itemClick()
        }
        holder as ViewHolder
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun refreshView(position: Int, newItems: List<CartItem>) {
        cartItems = newItems
        notifyItemRemoved(position)
    }

    class ViewHolder(view: View, val itemClick: () -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: CartItem) = with(itemView) {
            cartImg.loadImg(item.product.image)
            cartTitle.text = item.product.name
            cartAmount.text = item.amount.toString()
            cartItemPrice.text = BigDecimal(item.product.price * item.amount)
                .setScale(2, RoundingMode.DOWN)
                .toString() + " CHF"

            Observable.create(ObservableOnSubscribe<CartItem> {
                addAmount.setOnClickListener { view ->
                    CartCompanion.addToCart(item, item.product.id.toString(), context)
                    itemClick()
                    it.onNext(CartCompanion.getCartItem(context, item.product.id.toString()))
                }

                removeAmount.setOnClickListener { view ->
                    CartCompanion.removeCartItem(
                        CartCompanion.getCartItem(context, item.product.id.toString()), //wird statt "item" benutzt um das aktualisierte Item mit neuem Amount zu erhalten
                        item.product.id.toString(),
                        context
                    )
                    itemClick()
                    it.onNext(CartCompanion.getCartItem(context, item.product.id.toString()))
                }
            }).subscribe {cart ->
                cartAmount.text = cart.amount.toString()
                cartItemPrice.text = BigDecimal(cart.product.price * cart.amount)
                    .setScale(2, RoundingMode.DOWN)
                    .toString() + " CHF"
            }
        }
    }
}