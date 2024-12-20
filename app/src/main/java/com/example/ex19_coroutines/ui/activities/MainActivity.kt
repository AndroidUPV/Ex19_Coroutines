/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex19_coroutines.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ex19_coroutines.R
import com.example.ex19_coroutines.databinding.ActivityMainBinding

/**
 * Displays a series of buttons that launch different coroutines.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // Enable edge-to-edge display
        enableEdgeToEdge()
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)

        // Make the custom ToolBar the ActionBar
        setSupportActionBar(binding.toolbar)
        // Get an instance of the NavController.
        // findNavController() does not work properly with FragmentContainerView in onCreate()
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        // The AppBarConfiguration sets as starting fragments (without Up navigation icon)
        // those highlighted as Home in the navigation graph
        appBarConfiguration = AppBarConfiguration(navController.graph)
        // Configure the ActionBar to work with the NavController,
        // so that its title is updated when navigating
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * Manages the Up navigation.
     * First it tries to navigate Up in the navigation hierarchy from the NavController and,
     * if it does not succeed, then tries again from the AppCompatActivity.
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            findNavController(R.id.navHostFragment),
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}