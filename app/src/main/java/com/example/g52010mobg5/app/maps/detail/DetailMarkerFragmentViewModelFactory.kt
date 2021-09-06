package com.example.g52010mobg5.app.maps.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.g52010mobg5.app.database.user.favorite.FavoriteDatabaseDao

class DetailMarkerFragmentViewModelFactory(
    private val dataSource: FavoriteDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMarkerFragmentViewModel::class.java)) {
            return DetailMarkerFragmentViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}