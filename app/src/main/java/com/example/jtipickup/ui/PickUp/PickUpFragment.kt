package com.example.jtipickup.ui.PickUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jtipickup.R

class PickUpFragment : Fragment() {

    private lateinit var pickUpViewModel: PickUpViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        pickUpViewModel =
                ViewModelProvider(this).get(PickUpViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pickup, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        pickUpViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}