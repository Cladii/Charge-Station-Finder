package com.example.g52010mobg5.app.maps.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.g52010mobg5.app.database.user.favorite.Favorite
import com.example.g52010mobg5.app.database.user.favorite.FavoriteDatabaseDao
import kotlinx.coroutines.launch

class DetailMarkerFragmentViewModel(
    private val database: FavoriteDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val isPresent = MutableLiveData<Boolean>()

    fun onAddFav(fav_info: String) {
        viewModelScope.launch {
            val fav = getFav(fav_info)
            database.insert(fav)
            isPresent(fav)
        }
    }

    fun onDelFav(fav_info: String) {
        viewModelScope.launch {
            val fav = getFav(fav_info)
            database.delete(fav)
            isPresent(fav)
        }
    }

    fun isPresent(fav: Favorite) {
        viewModelScope.launch {
            isPresent.value = database.get(fav.addressIdid) == null
        }
    }

    fun getFav(fav_info: String): Favorite {
        return Favorite(
            fav_info.substringAfter("Adresse : ").substringBefore("\n"),
            fav_info.substringAfter("Coordonnées : ").substringBefore(" ").toDouble(),
            fav_info.substringAfter(" / ").substringBefore("\n").toDouble(),
            fav_info.substringAfter("Type : ").substringBefore("\n"),
            fav_info.substringAfter("Usage : "),
        )
    }
}