package com.saie.dogscompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Amber500,
    primaryVariant = Amber700,
    background = Color.White,
    surface = Amber200,
    secondary = Brown200,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Brown200
)

private val LightColorPalette = lightColors(
    primary = Amber500,
    primaryVariant = Amber700,
    background = Color.White,
    surface = Amber200,
    secondary = Brown200,
    onPrimary = Color.White,
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
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
