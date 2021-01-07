package com.theapache64.topcorn2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 20:52
 */
val LightColors = lightColors(
    primary = Color.Black,
    primaryVariant = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)
val DarkColors = darkColors(
    primary = Color.White,
    primaryVariant = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

@Composable
fun TopCornTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = TopCornTypography,
        colors = if (darkTheme) DarkColors else LightColors
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}