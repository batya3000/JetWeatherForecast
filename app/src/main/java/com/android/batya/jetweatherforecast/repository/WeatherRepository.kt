package com.android.batya.jetweatherforecast.repository


import com.android.batya.jetweatherforecast.data.DataOrException
import com.android.batya.jetweatherforecast.model.Weather
import com.android.batya.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String) : DataOrException<Weather, Boolean, Exception> {

        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}