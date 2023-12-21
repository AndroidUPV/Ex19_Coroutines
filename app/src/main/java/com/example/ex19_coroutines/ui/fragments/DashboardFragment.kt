/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex19_coroutines.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ex19_coroutines.R
import com.example.ex19_coroutines.databinding.FragmentDashboardBinding
import com.example.ex19_coroutines.ui.viewmodels.CoroutinesViewModel

/**
 * Displays a series of buttons that launch different coroutines.
 * Their progress can be checked in Logcat.
 */
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    // Reference to a ViewModel shared between Fragments
    private val viewModel: CoroutinesViewModel by viewModels()

    // Backing property to resource binding
    private var _binding: FragmentDashboardBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentDashboardBinding.bind(view)
        // Launch a single coroutine
        binding.bSingleCoroutine.setOnClickListener { viewModel.launchSingleCoroutine() }
        // Launch several coroutines defined as suspend functions
        binding.bSuspendFunction.setOnClickListener { viewModel.launchSuspendFunction() }
        // Launch asynchronous coroutines and wait for their result
        binding.bAsyncCoroutines.setOnClickListener { viewModel.launchAsyncCoroutines() }
        // Launch coroutines with different CoroutineDispatcher
        binding.bWithContext.setOnClickListener { viewModel.launchCoroutinesWithContext() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }
}