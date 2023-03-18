package com.android.batya.jetweatherforecast.data

import androidx.room.*
import com.android.batya.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import com.android.batya.jetweatherforecast.model.Unit


@Dao
interface WeatherDao {

    // Favorite table

    @Query("SELECT *  from fav_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from fav_table WHERE city =:city")
    suspend fun fetFavById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_table")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    // Settings table

    @Query("SELECT *  from settings_table")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from settings_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)
}