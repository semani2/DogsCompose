package com.saie.dogscompose.data

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
@Immutable
data class Breed(
    @PrimaryKey val id: Int,
    val name: String,
    @SerializedName("reference_image_id") val imageId: String,
    val origin: String?,
    @SerializedName("life_span")val lifeSpan: String
)
