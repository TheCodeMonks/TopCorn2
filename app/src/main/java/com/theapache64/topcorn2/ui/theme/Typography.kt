
package com.theapache64.topcorn2.ui.theme


import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.sp
import com.theapache64.topcorn2.R

/**
 * Created by theapache64 : Jan 03 Sun,2021 @ 22:27
 */
private val GoogleSans = fontFamily(
    font(R.font.google_sans_regular),
    font(R.font.google_sans_medium, FontWeight.W500),
    font(R.font.google_sans_bold, FontWeight.W600)
)

val TopCornTypography = Typography(
    h4 = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = GoogleSans,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)