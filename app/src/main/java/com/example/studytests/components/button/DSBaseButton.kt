package com.example.studytests.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studytests.components.button.styles.ButtonType
import com.example.studytests.components.button.styles.data.DSButtonColors
import com.example.studytests.components.button.styles.data.DSButtonStyles

@Composable
internal fun BaseButton(config: DSButtonData, buttonTheme: DSButtonStyles) {
    val colors = when (config.type) {
        is ButtonType.Primary -> buttonTheme.colors.primary
        is ButtonType.Secondary -> buttonTheme.colors.secondary
        is ButtonType.Action -> buttonTheme.colors.action
        is ButtonType.Navigation -> buttonTheme.colors.navigation
        else -> DSButtonColors(Color.Gray, Color.Black)
    }

    val shape = buttonTheme.shapes.default
    val textStyle = buttonTheme.typography.default

    Box(
        modifier = Modifier
            .clip(shape)
            .background(colors.background)
            .border(1.dp, colors.border, shape)
            .clickable(enabled = config.enabled, onClick = config.onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (config.type is ButtonType.Action) {
                config.icon?.invoke()
                Spacer(modifier = Modifier.width(8.dp))
            }
            config.text?.let {
                androidx.compose.foundation.text.BasicText(text = it, style = textStyle.copy(color = colors.content))
            }
            if (config.type is ButtonType.Navigation) {
                Spacer(modifier = Modifier.width(8.dp))
                config.icon?.invoke()
            }
        }
    }
}

