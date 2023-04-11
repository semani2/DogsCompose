package com.saie.dogscompose.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saie.dogscompose.data.Breed

@Database(entities = [Breed::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao
}
