package com.victoweng.ciya2.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.victoweng.ciya2.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.main_navigation_fragment)
        setupWithNavController(bottom_nav_view, navController)
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this,
        R.id.main_navigation_fragment
    ).navigateUp()

}
