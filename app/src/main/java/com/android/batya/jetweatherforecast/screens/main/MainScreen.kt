package com.android.batya.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.batya.jetweatherforecast.data.DataOrException
import com.android.batya.jetweatherforecast.model.Weather
import com.android.batya.jetweatherforecast.model.WeatherItem
import com.android.batya.jetweatherforecast.navigation.WeatherScreens
import com.android.batya.jetweatherforecast.screens.settings.SettingsViewModel
import com.android.batya.jetweatherforecast.utils.formatDate
import com.android.batya.jetweatherforecast.utils.formatDecimals
import com.android.batya.jetweatherforecast.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?,
) {
    val curCity: String = if (city!!.isBlank()) "Moscow" else city

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember { mutableStateOf("imperial") }
    var isImperial by remember { mutableStateOf(false) }

    if (unitFromDb.isNotEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)) {
            value = mainViewModel.getWeatherData(
                city = curCity,
                units = unit
            )
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator(modifier = Modifier.size(15.dp))
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController, isImperial = isImperial)
        }
    }

    Log.d("TAG", "MainScreen: $city")

}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title ="${weather.city.name}, ${weather.city.country}",
               // icon = Icons.Default.ArrowBack,
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            ) {
                Log.d("TAG", "MainScaffold: Button clicked")
            }
        }
    ) {
        MainContent(data = weather, isImperial = isImperial)


    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = formatDate(weatherItem.dt), // Wed Nov 30
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFEEEEEE),
                                Color(0xFFB6B6B6)
                            )
                        )
                    )
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }

        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)

        Divider()

        SunsetSunRiseRow(weather = weatherItem)

        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = "This week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(15.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(8.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) {item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}

