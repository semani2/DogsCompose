package com.saie.dogscompose.network

import com.saie.dogscompose.data.Breed
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsService {

    @GET("v1/breeds")
    suspend fun fetchBreeds(@Query("limit") limit: Int, @Query("page") page: Int):
            ApiResponse<List<Breed>>
}