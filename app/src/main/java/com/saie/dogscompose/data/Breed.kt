package com.saie.dogscompose.data

import androidx.compose.runtime.Immutable

@Immutable
data class Breed(
    val id: Int,
    val name: String,
    val imageId: String,
    val countryCode: String,
    val origin: String,
    val lifeSpan: String
)