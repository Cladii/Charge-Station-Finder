package com.example.g52010mobg5.app.database.user.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @PrimaryKey @ColumnInfo(name = "addressId") val addressIdid: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "usage") val usage: String
)