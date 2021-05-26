package com.example.jtipickup

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jtipickup.ui.cart.CartFragment
import com.example.jtipickup.ui.home.HomeFragment
import com.example.jtipickup.ui.products.ProductsFragment
import com.example.jtipickup.ui.login.LoginFragment
import com.example.jtipickup.ui.map.MapsFragment
import com.example.jtipickup.ui.profile.ProfileFragment
import com.google.android.gms.maps.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start Home Fragment
        loadFragment("home")

        // Alternative Methode für Bottomnav mit dynamischen Fragment Loads.
        // nav_view Binding via Kotlin Synthetic Binding
        nav_view.setOnNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.navigation_home -> {
                    loadFragment("home")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_products -> {
                    loadFragment("products")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_pickup -> {
                    loadFragment("map")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    loadFragment("cart")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
           R.id.profile -> supportFragmentManager.beginTransaction().replace(R.id.container,  LoginFragment()).commit()
        }
        return super.onOptionsItemSelected(item)
    }

    //Funktion zum Laden von Fragments
    private fun loadFragment(fragmentTag: String) {
        // load fragment
        var activity: MainActivity = this
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        var fragment: Fragment? = fragmentManager.findFragmentByTag("fragmentTag")
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment == null) {
            when(fragmentTag){
                "home" -> fragment = HomeFragment()
                "products" -> fragment = ProductsFragment()
                "map" -> fragment = MapsFragment()
                "profile" -> fragment = ProfileFragment()
                "cart" -> fragment = CartFragment()
            }
        }
        transaction.replace(R.id.container, fragment!!, fragmentTag)
        transaction.addToBackStack(fragmentTag)
        transaction.commit()
    }
}