package com.example.g52010mobg5.app.database.email

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "email_connection_table")
data class Email(
    @PrimaryKey @ColumnInfo(name = "emailId") val emailId: String,
    @ColumnInfo(name = "date_connection") var date: String
)