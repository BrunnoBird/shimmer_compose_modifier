package com.example.studytests.components.shimmer

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

fun Modifier.shimmerEffect(
    enabled: Boolean = true,
    // Parâmetros para animação customizável
    shimmerColors: List<Color> = listOf(
        Color(0xFFB8B5B5),
        Color(0xFF8F8B8B),
        Color(0xFFB8B5B5),
    ),
    gradientWidthFactor: Float = 2f, // Pode ajustar conforme necessário
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 1000) // ou use keyframes conforme acima
): Modifier = composed {
    var bg = Color(0xFF8F8B8B)
    var size by remember { mutableStateOf(IntSize.Zero) }
    // Caso o efeito esteja desativado, não altera o conteúdo
    if (!enabled) return@composed this

    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                // Frame 1: de 0ms a 500ms
                -2 * size.width.toFloat() at 0 with LinearEasing
                -1 * size.width.toFloat() at 500 with LinearEasing
                // Frame 2: de 500ms a 1000ms
                1 * size.width.toFloat() at 1000 with LinearOutSlowInEasing
                // Frame 3: de 1000ms a 1500ms
                2 * size.width.toFloat() at 1500 with FastOutLinearInEasing
            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )


    // Usando drawWithContent para "substituir" o conteúdo
    this
        .onGloballyPositioned { size = it.size }
        .drawWithContent {
            background(color = bg)
            // Aqui não chamamos drawContent() para esconder os filhos
            // Em vez disso, desenhamos apenas o efeito shimmer
            val brush = Brush.horizontalGradient(
                colors = shimmerColors,
                startX = startOffsetX,
                endX = startOffsetX + size.width.toFloat()
            )
            drawRect(brush = brush)
        }
}
