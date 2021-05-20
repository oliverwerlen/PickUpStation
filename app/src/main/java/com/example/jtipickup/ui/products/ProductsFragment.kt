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
import com.example.jtipickup.response.PickUpResponse
import com.example.jtipickup.response.ProductsResponse
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {

    private lateinit var productsViewModel: ProductsViewModel
    var products: List<ProductsResponse> = emptyList()
    val productList = mutableListOf<ProductItem>()

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

        if(savedInstanceState == null) {
            productsViewModel.prodcuts.observe(viewLifecycleOwner, Observer {
                this.products = it
                initAdapter()
                for(item in products)fillList(item)
                (products_list.adapter as ProductsAdapter).addProducts(productList)
            })
            productsViewModel.getAllProducts(requireContext())
        }
    }

    fun fillList(productsResponse: ProductsResponse) {
        productList.add(
            ProductItem(
            productsResponse.id,
            productsResponse.productCode,
                productsResponse.name,
                productsResponse.description,
                productsResponse.price,
                "https://bdaf21-owerlen.enterpriselab.ch/assets/min/"+productsResponse.image+"-min.png"
        ))
    }

    private fun initAdapter() {
        if (products_list.adapter == null) {
            products_list.adapter = ProductsAdapter()
        }
    }
}