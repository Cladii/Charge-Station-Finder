package com.example.g52010mobg5.app.maps

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.g52010mobg5.app.database.user.favorite.FavoriteDatabaseDao
import com.example.g52010mobg5.app.network.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapsFragmentViewModel(
    private val database: FavoriteDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _response = MutableLiveData<List<ChargeStation>>()

    val response: LiveData<List<ChargeStation>>
        get() = _response

    private lateinit var pos: LatLng

    fun setPos(pos: LatLng) {
        this.pos = pos
    }

    fun getTypeChargeStation(type: Type) {
        viewModelScope.launch {
            try {
                _response.value = ChargeApi.retrofitService.getChargeStations(
                    pos.latitude,
                    pos.longitude,
                    type.id
                )

            } catch (e: java.lang.Exception) {
                Log.i("MapsFragmentViewModel", e.message!!)
            }
        }
    }

    fun getChargeStation() {
        viewModelScope.launch {
            try {
                _response.value =
                    ChargeApi.retrofitService.getChargeStations(pos.latitude, pos.longitude)
            } catch (e: Exception) {
                Log.i("MapsFragmentViewModel", e.message!!)
            }
        }
    }

    suspend fun getFavorite() {
        _response.value = database.getAll().map {
            val type = Type.parse(it.type)
            ChargeStation(
                UsageType(null, null, it.usage),
                StatusType(null, null),
                AdressInfo(null, it.addressIdid, null, null,
                    null, it.latitude, it.longitude, null),
                listOf(
                    Connections(
                        ConnectionType(type!!.id, type.classicName, type.technicalName),
                        null,
                        null,
                        null
                    )
                ),
                null
            )
        }
    }
}