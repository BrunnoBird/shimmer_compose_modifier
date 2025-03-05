package com.example.studytests.components.button.styles

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studytests.components.button.styles.data.DSButtonColorScheme
import com.example.studytests.components.button.styles.data.DSButtonColors
import com.example.studytests.components.button.styles.data.DSButtonShapes
import com.example.studytests.components.button.styles.data.DSButtonStyles
import com.example.studytests.components.button.styles.data.DSButtonTypography

val localDSButtonStyle = compositionLocalOf<DSButtonStyles?> { null }

object DSButtonStyleDefaults {
    val Default = DSButtonStyles(
        colors = DSButtonColorScheme(
            primary = DSButtonColors(
                background = Color(0xFF6200EE),
                content = Color.White
            ),
            secondary = DSButtonColors(
                background = Color.Gray,
                content = Color.Black
            ),
            icon = DSButtonColors(
                background = Color.Transparent,
                content = Color.Black
            ),
            action = DSButtonColors(
                background = Color.Transparent,
                content = Color.Blue
            ),
            navigation = DSButtonColors(
                background = Color.Transparent,
                content = Color.Black
            )
        ),
        typography = DSButtonTypography(
            default = TextStyle(fontSize = 16.sp),
            bold = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        ),
        shapes = DSButtonShapes(
            default = RoundedCornerShape(8.dp),
            rounded = RoundedCornerShape(50)
        )
    )
}



