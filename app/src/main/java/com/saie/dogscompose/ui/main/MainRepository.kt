package com.saie.dogscompose.ui.main

import androidx.annotation.WorkerThread
import com.saie.dogscompose.network.DogsService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dogsService: DogsService
) {

    init {
        Timber.d("Injection MainRepository")
    }

    @WorkerThread
    fun loadDogBreeds(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        dogsService.fetchBreeds()
            .suspendOnSuccess {
                emit(data)
            }
            .onFailure { onError(message()) }
    }.onStart { onStart() }
        .onCompletion { onCompletion() }
        .flowOn(Dispatchers.IO)
}
