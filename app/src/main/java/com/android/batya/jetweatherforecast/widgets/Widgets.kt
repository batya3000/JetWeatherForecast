package com.android.batya.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.android.batya.jetweatherforecast.R
import com.android.batya.jetweatherforecast.model.WeatherItem
import com.android.batya.jetweatherforecast.utils.formatDate
import com.android.batya.jetweatherforecast.utils.formatDateTime
import com.android.batya.jetweatherforecast.utils.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = formatDate(weather.dt).split(",")[0]
            )

            WeatherStateImage(
                imageUrl = imageUrl,
                imageSize = 60.dp
            )

            Surface(
                modifier = Modifier.padding(0.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFEEEEEE)
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = weather.weather[0].description,
                    style = MaterialTheme.typography.caption
                )
            }

            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(formatDecimals(weather.temp.max) + "°")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                        )
                    ) {
                        append(formatDecimals(weather.temp.min) + "°")
                    }
                }
            )
        }
    }
}

@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise Icon"
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row() {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.caption
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset Icon"
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon"
            )
            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row() {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon"
            )
            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }
        Row() {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind Icon"
            )
            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = "${formatDecimals(weather.speed)} " + if(isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun WeatherStateImage(
    imageUrl: String,
    imageSize: Dp = 80.dp
) {
    Image(
        modifier = Modifier.size(imageSize),
        painter = rememberAsyncImagePainter(

            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build()
        ),
        contentDescription = "Icon Image"
    )
}