package com.saie.dogscompose.ui.main

import androidx.annotation.WorkerThread
import com.saie.dogscompose.network.DogsService
import com.saie.dogscompose.persistence.BreedDao
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
    private val dogsService: DogsService,
    private val breedDao: BreedDao
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
        val breeds = breedDao.getBreeds()
        if (breeds.isEmpty()) {
            dogsService.fetchBreeds()
                .suspendOnSuccess {
                    breedDao.insertBreedList(data)
                    emit(data)
                }
                .onFailure { onError(message()) }
        } else {
            emit(breeds)
        }
    }.onStart { onStart() }
        .onCompletion { onCompletion() }
        .flowOn(Dispatchers.IO)
}
