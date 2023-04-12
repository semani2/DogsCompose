package com.saie.dogscompose.network

import com.saie.dogscompose.data.Breed
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DogsService {

    @GET("v1/breeds")
    suspend fun fetchBreeds(@Query("limit") limit: Int = 100, @Query("page") page: Int = 0):
            ApiResponse<List<Breed>>

    @GET("v1/breeds/{id}")
    suspend fun fetchBreed(@Path(value = "id", encoded = true) id: Int): ApiResponse<Breed>
}
