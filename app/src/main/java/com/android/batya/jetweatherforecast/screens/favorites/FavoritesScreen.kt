package com.android.batya.jetweatherforecast.screens.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.batya.jetweatherforecast.model.Favorite
import com.android.batya.jetweatherforecast.navigation.WeatherScreens
import com.android.batya.jetweatherforecast.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Favorite Cities",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController,
            ) { navController.popBackStack() }

        }
    ) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list: List<Favorite> = viewModel.favList.collectAsState().value

                LazyColumn() {
                    items(items = list) {
                        CityRow(it, navController, viewModel)
                    }
                }


            }
        }
    }


}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")

            },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = favorite.city)

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = favorite.country,
                    style = MaterialTheme.typography.caption
                )
            }

            Icon(
                modifier = Modifier.clickable {
                    viewModel.deleteFavorite(favorite)
                },
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Icon",
                tint = Color.Red.copy(alpha = 0.4f)
            )
        }
    }

}
