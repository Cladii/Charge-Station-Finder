package com.example.g52010mobg5.app.database.user.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDatabaseDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite_table")
    suspend fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite_table WHERE addressId = :key")
    suspend fun get(key: String): Favorite?

    @Delete
    suspend fun delete(favorite: Favorite)
}