package com.android.batya.jetweatherforecast.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.batya.jetweatherforecast.navigation.WeatherScreens
import com.android.batya.jetweatherforecast.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Search",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false
            ) {
                // onClick
                navController.popBackStack()
            }
        }
    ) {
        Surface() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .align(Alignment.CenterHorizontally),
                ) { mCity ->
                    //navController.popBackStack()
                    navController.navigate(WeatherScreens.MainScreen.name + "/$mCity")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,

) {
    val searchQueryState = rememberSaveable() { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column (
        modifier = modifier
    ) {
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Seattle",
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch.invoke(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .focusRequester(focusRequester),
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = placeholder) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black,
        ),
        shape = RoundedCornerShape(16.dp),
    )
}