package com.example.studytests.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.studytests.components.button.styles.DSButtonStyleDefaults
import com.example.studytests.components.button.styles.ButtonType
import com.example.studytests.components.button.styles.data.DSButtonStyles
import com.example.studytests.components.button.styles.localDSButtonStyle

@Composable
fun DSButton(
    config: DSButtonData,
    style: DSButtonStyles? = null
) {
    val buttonStyles = style ?: localDSButtonStyle.current ?: DSButtonStyleDefaults.Default

    when (config.type) {
        is ButtonType.Primary, is ButtonType.Secondary, is ButtonType.Action, is ButtonType.Navigation -> {
            BaseButton(config, buttonStyles)
        }

        is ButtonType.Icon -> {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(buttonStyles.shapes.rounded)
                    .background(buttonStyles.colors.icon.background)
                    .clickable(enabled = config.enabled, onClick = config.onClick),
                contentAlignment = Alignment.Center
            ) {
                config.icon?.invoke()
            }
        }

        is ButtonType.Sealed -> {
            Column {
                BaseButton(config.copy(type = ButtonType.Primary), buttonStyles)
                Spacer(modifier = Modifier.height(8.dp))
                BaseButton(config.copy(type = ButtonType.Secondary), buttonStyles)
            }
        }

        is ButtonType.Piled -> {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BaseButton(config.copy(type = ButtonType.Primary, text = "Confirmar"), buttonStyles)
                BaseButton(config.copy(type = ButtonType.Secondary, text = "Cancelar"), buttonStyles)
            }
        }
    }
}

