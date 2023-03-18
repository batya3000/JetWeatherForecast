package com.android.batya.jetweatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.batya.jetweatherforecast.R
import com.android.batya.jetweatherforecast.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val defaultCity = "Kaliningrad"
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(3f)
                        .getInterpolation(it)
                }
            )
        )
        delay(1300L)
        navController.navigate(WeatherScreens.MainScreen.name + "/$defaultCity") {
            popUpTo(WeatherScreens.SplashScreen.name) {
                inclusive = true
            }
        }
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(300.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(1.5.dp, color = Color.LightGray),
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "Sunny Icon",
                contentScale = ContentScale.Fit
            )
            Text(
                text = "Weather App",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray
            )
        }
    }
}