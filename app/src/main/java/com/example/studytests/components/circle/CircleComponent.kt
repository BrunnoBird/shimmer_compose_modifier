package com.example.studytests.components.circle

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingCircle(
    isFinished: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    strokeWidth: Dp = 8.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    var rotation by remember { mutableStateOf(0f) }
    var isFilling by remember { mutableStateOf(false) }
    var fillProgress by remember { mutableStateOf(0f) }
    var isVisible by remember { mutableStateOf(true) }

    // Animação de rotação infinita
    val rotationAnim = rememberInfiniteTransition()
    val animatedRotation by rotationAnim.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )

    // Espera completar a rotação antes de iniciar o preenchimento
    LaunchedEffect(isFinished) {
        if (isFinished) {
            while (rotation % 360 != 0f) {
                delay(16L) // Aguarda completar o ciclo
            }
            isFilling = true
        }
    }

    // Animação de preenchimento após finalização
    LaunchedEffect(isFilling) {
        if (isFilling) {
            animate(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            ) { value, _ ->
                fillProgress = value
            }
            delay(300) // Pequena pausa antes de desaparecer
            isVisible = false
        }
    }

    if (!isVisible) return

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            rotation = animatedRotation % 360

            val radius = size.toPx() / 2
            val strokePx = strokeWidth.toPx()

            // Borda Circular Clara
            drawCircle(
                color = color.copy(alpha = 0.2f),
                radius = radius - strokePx / 2,
                style = Stroke(width = strokePx)
            )

            // Indicador de Carregamento (Arco)
            if (!isFilling) {
                drawArc(
                    color = color,
                    startAngle = rotation,
                    sweepAngle = 90f,
                    useCenter = false,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }

            // Animação de preenchimento do arco ao finalizar
            if (isFilling) {
                drawArc(
                    color = color,
                    startAngle = 0f,
                    sweepAngle = fillProgress * 360f,
                    useCenter = false,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }
        }

        // Conteúdo Central (Imagem, Ícone, etc.)
        content()
    }
}

