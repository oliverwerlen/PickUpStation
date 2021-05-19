package com.example.jtipickup.ui.products

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jtipickup.commons.AdapterConstants
import com.example.jtipickup.commons.ViewType
import com.example.jtipickup.commons.ViewTypeDelegateAdapter

class ProductsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.PRODUCTS, ProductsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

    fun addProducts(products: List<ProductItem>) {
        val initialPos = items.size - 1
        items.removeAt(initialPos)
        notifyItemRemoved(initialPos)
        items.addAll(products)
        notifyItemRangeChanged(initialPos, items.size + 1)
    }

    fun clearAndAddProducts(news: List<ProductItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())
        items.addAll(news)
        notifyItemRangeInserted(0, items.size)
    }

    fun getPrducts(): List<ProductItem> {
        return items
            .filter { it.getViewType() == AdapterConstants.PRODUCTS }
            .map { it as ProductItem }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}