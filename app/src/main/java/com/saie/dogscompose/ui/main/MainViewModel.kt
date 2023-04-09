package com.saie.dogscompose.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.saie.dogscompose.data.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    init {
        Timber.d("Injection MainViewModel")
    }

    val breedList: Flow<List<Breed>> = mainRepository.loadDogBreeds(
        onStart = { _isLoading.value = true },
        onCompletion = { _isLoading.value = false },
        onError = { Timber.d(it) }
    )

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading
}
