package com.example.jtipickup.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jtipickup.R
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {

    private lateinit var productsViewModel: ProductsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        productsViewModel =
                ViewModelProvider(this).get(ProductsViewModel::class.java)
        return container?.inflate(R.layout.fragment_products)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        products_list.setHasFixedSize(true)
        products_list.layoutManager = LinearLayoutManager(context)
        initAdapter()

        if(savedInstanceState == null) {
            val products = mutableListOf<ProductItem>()
            for(i in 1..10) {
                products.add(ProductItem(
                    i,
                    "$i",
                    "Titel$i",
                    "Test Beschreibung $i",
                    20.50,
                    "https://picsum.photos/200/200?image=$i"
                ))
            }
            (products_list.adapter as ProductsAdapter).addProducts(products)
        }
    }

    private fun initAdapter() {
        if (products_list.adapter == null) {
            products_list.adapter = ProductsAdapter()
        }
    }
}