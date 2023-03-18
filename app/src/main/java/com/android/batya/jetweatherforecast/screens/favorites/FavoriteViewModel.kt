package com.android.batya.jetweatherforecast.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.batya.jetweatherforecast.model.Favorite
import com.android.batya.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList: StateFlow<List<Favorite>> = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isNullOrEmpty()) {
                    Log.d("TAG", ": Empty or null favs")
                } else {
                    _favList.value = listOfFavs
                    Log.d("TAG", ": ${favList.value}")

                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch { repository.insertFavorite(favorite) }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch { repository.updateFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { repository.deleteFavorite(favorite) }


}