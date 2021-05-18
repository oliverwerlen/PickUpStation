package com.example.jtipickup

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
import com.example.jtipickup.ui.PickUp.PickUpFragment
import com.example.jtipickup.ui.home.HomeFragment
import com.example.jtipickup.ui.products.ProductsFragment
import com.example.jtipickup.ui.login.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Alternative Methode für Bottomnav mit dynamischen Fragment Loads.
        // nav_view Binding via Kotlin Synthetic Binding
        nav_view.setOnNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_products -> {
                    loadFragment(ProductsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_pickup -> {
                    loadFragment(PickUpFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        /*val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_pickup, R.id.navigation_products))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
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
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}