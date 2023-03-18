package com.android.batya.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.batya.jetweatherforecast.screens.about.AboutScreen
import com.android.batya.jetweatherforecast.screens.favorites.FavoritesScreen
import com.android.batya.jetweatherforecast.screens.main.MainScreen
import com.android.batya.jetweatherforecast.screens.main.MainViewModel
import com.android.batya.jetweatherforecast.screens.search.SearchScreen
import com.android.batya.jetweatherforecast.screens.settings.SettingsScreen
import com.android.batya.jetweatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val mainRoute = WeatherScreens.MainScreen.name
        composable(
            "$mainRoute/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel: MainViewModel = hiltViewModel()
                MainScreen(
                    navController,
                    mainViewModel,
                    city = city
                )
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavouriteScreen.name) {
            FavoritesScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }


    }
}