/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex19_coroutines.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext

// Constant for the Log TAG
const val COROUTINE_TAG = "com.example.ex19_coroutines.ui.viewmodels.COROUTINE"

/**
 * Holds no information.
 * It is used just to launch the different coroutines as it should be its duty.
 */
class CoroutinesViewModel : ViewModel() {

    /**
     * Logs the name of the current thread, the name of the coroutine (from the GlobalScope context) and the message.
     */
    private fun logGlobalScope(message: String) = Log.d(
        COROUTINE_TAG,
        "${Thread.currentThread().name}[${coroutineContext[CoroutineName]?.name}]: $message"
    )

    /**
     * Logs the name of the current thread, the name of the coroutine and the message.
     */
    private suspend fun log(message: String) = Log.d(
        COROUTINE_TAG,
        "${Thread.currentThread().name}[${kotlin.coroutines.coroutineContext[CoroutineName]?.name}]: $message"
    )

    /**
     * Launches a coroutine tied to the scope of this ViewModel.
     */
    fun launchSingleCoroutine() {
        viewModelScope.launch(CoroutineName("SingleCoroutine")) {
            delay(1000)
            log("World!")
        }
        logGlobalScope("Hello")
    }

    /**
     * Launches a coroutine tied to the scope of this ViewModel which launches a suspend function.
     */
    fun launchSuspendFunction() {
        viewModelScope.launch(CoroutineName("SuspendFunction")) {
            doWorld()
            log("Done")
        }
    }

    /**
     * Launches a couple of coroutines within this scope (ViewModel)
     */
    private suspend fun doWorld() = coroutineScope {
        launch(CoroutineName("Launch1")) {
            delay(2000)
            log("World 2")
        }
        launch(CoroutineName("Launch2")) {
            delay(1000)
            log("World 1")
        }
        log("Hello")
    }

    /**
     * Launches a coroutine tied to the scope of this ViewModel, which
     * asynchronously launches two suspend functions that are concurrently executed.
     * The coroutine awaits for result to be available before computing the operation.
     */
    fun launchAsyncCoroutines() {
        viewModelScope.launch(CoroutineName("AsyncCoroutines")) {
            log("Start")
            val firstOperand = async(CoroutineName("FirstOperand")) { getFirstOperand() }
            val secondOperand = async(CoroutineName("SecondOperand")) { getSecondOperand() }
            log("The sum is ${firstOperand.await() + secondOperand.await()}")
        }
    }

    /**
     * Suspend function that provides a random number as result.
     */
    private suspend fun getFirstOperand(): Int {
        delay(1000)
        val firstOp = (0..100).random()
        log("$firstOp")
        return firstOp
    }

    /**
     * Suspend function that provides a random number as result.
     */
    private suspend fun getSecondOperand(): Int {
        delay(1000)
        val secondOp = (200..500).random()
        log("$secondOp")
        return secondOp
    }

    /**
     * Launches a coroutine tied to the scope of this ViewModel, which
     * launches three suspend functions that are main-safe.
     */
    fun launchCoroutinesWithContext() {
        viewModelScope.launch(CoroutineName("WithContext")) {
            getDataFromWebServer()
            processData()
            displayData()
            log("Done")
        }
    }

    /**
     * Suspend function that is main-safe as it moves its execution to a IO thread.
     */
    private suspend fun getDataFromWebServer() {
        withContext(Dispatchers.IO) {
            log("Downloading data")
            delay(5000)
            log("Data received")
        }
    }

    /**
     * Suspend function that is main-safe as it moves its execution to a high performance (CPU) thread.
     */
    private suspend fun processData() {
        withContext(Dispatchers.Default) {
            log("Processing data")
            delay(3000)
            log("Data processed")
        }
    }

    /**
     * Suspend function that moves its execution to the main thread as it must update the UI.
     */
    private suspend fun displayData() {
        withContext(Dispatchers.Main) {
            log("Displaying data")
            delay(1000)
        }
    }

}