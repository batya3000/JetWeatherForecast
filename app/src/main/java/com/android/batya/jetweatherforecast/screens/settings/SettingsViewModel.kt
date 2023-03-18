package com.android.batya.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import com.android.batya.jetweatherforecast.model.Unit
import com.android.batya.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList: StateFlow<List<Unit>> = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isNullOrEmpty()) {
                    Log.d("TAG", ": Empty list")
                } else {
                    _unitList.value = listOfFavs
                   // Log.d("TAG", ": ${unitList.value}")

                }
            }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch { repository.insertUnit(unit) }
    fun updateUnit(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun deleteUnit(unit: Unit) = viewModelScope.launch { repository.deleteUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }
}