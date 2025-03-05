package com.example.studytests.components.button

import androidx.compose.runtime.Composable
import com.example.studytests.components.button.styles.ButtonType

data class DSButtonData(
    val text: String? = null,
    val icon: @Composable (() -> Unit)? = null,
    val type: ButtonType = ButtonType.Primary,
    val enabled: Boolean = true,
    val onClick: () -> Unit
)
