package com.android.batya.jetweatherforecast.repository

import com.android.batya.jetweatherforecast.data.WeatherDao
import com.android.batya.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.android.batya.jetweatherforecast.model.Unit


//In the real case we don't need to use second repository, all cases must be in

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun getFavById(city: String): Favorite = weatherDao.fetFavById(city)

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()


}