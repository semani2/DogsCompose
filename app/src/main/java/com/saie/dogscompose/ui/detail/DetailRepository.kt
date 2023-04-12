package com.saie.dogscompose.ui.detail

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

class DetailRepository @Inject constructor(
    private val dogsService: DogsService,
    private val breedDao: BreedDao
) {

    init {
        Timber.d("Injection DetailRepository")
    }

    @WorkerThread
    fun loadDogBreed(
        id: Int,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val breed = breedDao.getBreed(id)
        if (breed == null) {
            dogsService.fetchBreed(id)
                .suspendOnSuccess {
                    emit(data)
                }
                .onFailure { onError(message()) }
        } else {
            emit(breed)
        }
    }.onStart { onStart() }
        .onCompletion { onCompletion() }
        .flowOn(Dispatchers.IO)
}
