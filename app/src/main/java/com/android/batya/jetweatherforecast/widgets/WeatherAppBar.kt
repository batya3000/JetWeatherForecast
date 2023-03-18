package com.android.batya.jetweatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.batya.jetweatherforecast.model.Favorite
import com.android.batya.jetweatherforecast.navigation.WeatherScreens
import com.android.batya.jetweatherforecast.screens.favorites.FavoriteViewModel


@Composable
fun WeatherAppBar(
    title: String = "",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 1.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = { },
    onButtonClicked: () -> Unit = { },
) {
    val showDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(
                    onClick = {
                        showDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            } else Box() {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
            if (isMainScreen) {
                val isAlreadyInFavList = favoriteViewModel
                    .favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if (isAlreadyInFavList.isEmpty()) {
                    Icon(
                        modifier = Modifier
                            .scale(0.9f)
                            .padding(start = 10.dp)
                            .clickable {
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1].trim(),
                                    )
                                )
                                Toast.makeText(context, "${title.split(",")[0]} added to favorites", Toast.LENGTH_SHORT).show()
                            },
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Empty Favorite Icon",
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .scale(0.9f)
                            .padding(start = 10.dp)
                            .clickable {
                                favoriteViewModel.deleteFavorite(
                                    Favorite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1].trim(),
                                    )
                                )
                                Toast.makeText(context, "${title.split(",")[0]} deleted from favorites", Toast.LENGTH_SHORT).show()
                            },
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red.copy(alpha = 0.7f)
                    )

                }
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation,

    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if(showIt.value) {
        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf("About", "Favorites", "Settings", )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp),
    ) {
        DropdownMenu(
            modifier = Modifier
                .width(140.dp)
                .background(Color.White),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            }
        ) {
            items.forEachIndexed() { _, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showDialog.value = false
                        navController.navigate(
                            when(text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavouriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            }
                        )
                    }
                ) {
                    Icon(
                        imageVector = when(text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        },
                        tint = Color.LightGray,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(6.dp))

                    Text(
                        text = text,
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppBarPreview() {
    WeatherAppBar(
        title = "Title",
        navController = rememberNavController()
    )
}