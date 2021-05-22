package com.example.jtipickup.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jtipickup.R
import com.example.jtipickup.ui.login.SessionManager

class CartFragment: Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManager = SessionManager(requireContext())
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }
}