package com.saie.dogscompose.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.saie.dogscompose.data.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    detailRepository: DetailRepository
) : ViewModel() {

    init {
        Timber.d("Injection DetailViewModel")
    }

    private val breedIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    val breedDetailFlow = breedIdSharedFlow.flatMapLatest { id ->
        detailRepository.loadDogBreed(
            id,
            onStart = { _isLoading.value = true },
            onCompletion = { _isLoading.value = false },
            onError = { Timber.d(it) }
        )
    }

    fun loadBreedById(id: Int) = breedIdSharedFlow.tryEmit(id)

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

}
