package com.example.jtipickup.ui.map

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.jtipickup.R
import com.example.jtipickup.response.LoginResponse
import com.example.jtipickup.response.PickUpResponse
import com.example.jtipickup.retrofit.ApiClient
import com.example.jtipickup.ui.login.SessionManager
import com.example.jtipickup.ui.profile.ProfileFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener, View.OnClickListener {

    val pickUpViewModel: PickUpViewModel by viewModels()
    private val TAG = "PickUp"
    var pickUps: List<PickUpResponse> = emptyList()

    private lateinit var sessionManager: SessionManager
    private lateinit var googleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        pickUpViewModel.pickUps.observe(this, Observer {
            this.pickUps = it
            this.googleMap = googleMap
            for(item in pickUps)createMarker(item)
        })
        pickUpViewModel.getAllPickUps(requireContext())
        googleMap.setOnMarkerClickListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManager = SessionManager(requireContext())
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    fun createMarker(pickUpResponse: PickUpResponse){
        val longlat = LatLng(pickUpResponse.latitude.toDouble(), pickUpResponse.longitude.toDouble())
        val marker: Marker = googleMap.addMarker(MarkerOptions().position(longlat).title(pickUpResponse.name).snippet(pickUpResponse.description))
        marker.tag = pickUpResponse
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(longlat))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val pickUp = marker.tag as? PickUpResponse
        if(pickUp != null) {
            this.sessionManager.savePickUpSelected(pickUp)
        }
        val snackbar = Snackbar.make(
            requireActivity().findViewById(R.id.map) as View,
            "Hello",
            Snackbar.LENGTH_SHORT
        )
        snackbar.setAction("To the products", this)
        snackbar.show()
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    override fun onClick(v: View?) {
        goToProducts()
    }

    private fun goToProducts() {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragment: Fragment? = fragmentManager.findFragmentByTag("products")
            if(fragment != null){
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, fragment)
                fragmentTransaction.commit()
        }
    }

}