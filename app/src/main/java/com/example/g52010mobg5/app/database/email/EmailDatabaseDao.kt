package com.example.g52010mobg5.app.database.email

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface EmailDatabaseDao {

    @Insert
    suspend fun insert(email: Email)

    @Update
    suspend fun update(email: Email)

    @Query("SELECT * FROM email_connection_table")
    fun getAll(): LiveData<List<Email>>

    @Query("SELECT * FROM email_connection_table WHERE emailId = :key")
    suspend fun get(key: String): Email?
}