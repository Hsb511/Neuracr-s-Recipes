package com.example.design_system.theming

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.design_system.R

internal val neuracrTypography = Typography(
    displaySmall = TextStyle(
        //fontFamily = neuracrFontFamily(),
        fontWeight = titleFontWeight(),
        fontSize = 36.sp,
    ),
    headlineSmall = TextStyle(
        //fontFamily = neuracrFontFamily(),
        fontWeight = titleFontWeight(),
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        //fontFamily = neuracrFontFamily(),
        fontWeight = titleFontWeight(),
        fontSize = 22.sp,
    ),
    bodyLarge = TextStyle(
        //fontFamily = neuracrFontFamily(),
        fontWeight = bodyFontWeight(),
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        //fontFamily = neuracrFontFamily(),
        fontWeight = boldFontWeight(),
        fontSize = 14.sp,
    ),
)

private fun bodyFontWeight() = FontWeight.Light
private fun boldFontWeight() = FontWeight.Medium
private fun titleFontWeight() = FontWeight.ExtraBold

//private val robotoLight = Font(R.font.roboto_light, bodyFontWeight())
//private val robotoMedium = Font(R.font.roboto_medium, boldFontWeight())
private val orelega1 = Font(R.font.orelega_one, FontWeight.Bold)
private val orelega2 = Font(R.font.orelega_one, FontWeight.Medium)
private val orelega3 = Font(R.font.orelega_one, FontWeight.Normal)
private val orelega4 = Font(R.font.orelega_one, FontWeight.Light)
private val orelega5 = Font(R.font.orelega_one, FontWeight.ExtraBold)

private fun neuracrFontFamily() = FontFamily(orelega1, orelega2, orelega3, orelega4, orelega5) //robotoLight, robotoMedium) // orelegaOne))
