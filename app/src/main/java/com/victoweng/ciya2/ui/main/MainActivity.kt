package com.victoweng.ciya2.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.victoweng.ciya2.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    private val TAG = MainActivity::class.java.canonicalName

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.main_navigation_fragment)
        setupWithNavController(bottom_nav_view, navController)
        navController.addOnDestinationChangedListener {
            controller: NavController, destination: NavDestination, arguments: Bundle? ->

            when(destination.id) {
                R.id.loginFragment -> hideBottomNav()
                R.id.createUsernameFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        bottom_nav_view.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottom_nav_view.visibility = View.GONE
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this,
        R.id.main_navigation_fragment
    ).navigateUp()

}
