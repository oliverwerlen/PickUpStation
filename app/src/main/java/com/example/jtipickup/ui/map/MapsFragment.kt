package com.example.jtipickup.ui.map

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.jtipickup.R
import com.example.jtipickup.response.PickUpResponse
import com.example.jtipickup.retrofit.ApiClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment() {

    val pickUpViewModel: PickUpViewModel by viewModels()
    private val TAG = "PickUp"
    var pickUps: List<PickUpResponse> = emptyList()
    private lateinit var apiClient: ApiClient
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

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    fun createMarker(pickUpResponse: PickUpResponse){
        val sydney = LatLng(pickUpResponse.latitude.toDouble(), pickUpResponse.longitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(sydney).title(pickUpResponse.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }
    fun addMarker(map: GoogleMap){

    }
    fun getMarkerText(){

    }
}