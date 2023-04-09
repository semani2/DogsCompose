package com.saie.dogscompose.data

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Breed(
    val id: Int,
    val name: String,
    @SerializedName("reference_image_id") val imageId: String,
    val origin: String,
    @SerializedName("life_span")val lifeSpan: String
)
