package com.example.g52010mobg5.app.maps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.g52010mobg5.app.database.user.favorite.FavoriteDatabaseDao

class MapsFragmentViewModelFactory(
    private val dataSource: FavoriteDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsFragmentViewModel::class.java)) {
            return MapsFragmentViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}
