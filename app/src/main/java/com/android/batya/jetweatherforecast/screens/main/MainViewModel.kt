package com.android.batya.jetweatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.android.batya.jetweatherforecast.data.DataOrException
import com.android.batya.jetweatherforecast.model.Weather
import com.android.batya.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }

}