package com.android.batya.jetweatherforecast.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.batya.jetweatherforecast.model.Unit
import com.android.batya.jetweatherforecast.widgets.WeatherAppBar
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    var unitToggleState by remember { mutableStateOf(false) }
    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value

    val defaultChoice =
        if (choiceFromDb.isEmpty()) measurementUnits[0]
        else choiceFromDb[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Settings",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController,
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize(),) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 15.dp),
                    text = "Change units of Measurement"
                )
                IconToggleButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(5.dp)
                        .background(Color(0xFFEFBE42).copy(alpha = 0.4f)),
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) choiceState = "Imperial (F)"
                        else choiceState = "Metric (C)"
                    }
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit °F" else "Celsius °C")
                }
                
                Button(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    ),
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(Unit(unit = choiceState))
                        //navController.popBackStack()
                    },
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}