package com.saie.dogscompose.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saie.dogscompose.data.Breed

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreedList(breeds: List<Breed>)

    @Query("SELECT * FROM Breed")
    suspend fun getBreeds(): List<Breed>

    @Query("SELECT * FROM Breed WHERE id = :id_")
    suspend fun getBreed(id_: Int): Breed?
}
