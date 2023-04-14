package com.saie.dogscompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Amber500,
    primaryVariant = Amber700,
    background = Amber200,
    surface = Amber500,
    secondary = Brown200,
    onBackground = Brown200,
)

private val LightColorPalette = lightColors(
    primary = Amber500,
    primaryVariant = Amber700,
    background = Color.White,
    surface = Amber200,
    secondary = Brown200,
    onBackground = Brown200,
    onSecondary = Color.White,
    onSurface = Brown200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DogsComposeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Brown200
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = Amber700
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
