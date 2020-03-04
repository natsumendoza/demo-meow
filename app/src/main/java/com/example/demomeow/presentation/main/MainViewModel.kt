package com.example.demomeow.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demomeow.utils.SingleLiveEvent
import com.example.demomeow.utils.UseCaseResult
import com.example.demomeow.data.entities.Cat
import com.example.demomeow.data.repositories.CatRepository
import com.example.demomeow.presentation.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val catRepository: CatRepository) : BaseViewModel() {
    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val catsList = MutableLiveData<List<Cat>>()
    val showError = SingleLiveEvent<String>()
    val navigateToDetail = SingleLiveEvent<String>()

    init {
        // Load cats whem this ViewModel is instantiated
        loadCats()
    }

    fun loadCats() {
        // Show progressBar during the operation on the MAIN (default) thread
        showLoading.value = true
        // launch the Coroutine
        launch {
            // Switching from MAIN to IO thread for API operation
            // Update our data list with the new one from API
            val result = withContext(Dispatchers.IO) { catRepository.getCatList() }
            // Hide progressBar once the operation is done on the MAIN (default) thread
            showLoading.value = false
            when(result) {
                is UseCaseResult.Success -> catsList.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun catClicked(imageUrl: String) {
        navigateToDetail.value = imageUrl
    }
}