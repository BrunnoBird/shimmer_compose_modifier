package com.example.studytests.components.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Estado simples para o progresso do shimmer
data class ShimmerState(val progress: Float)

// Lembra e anima o estado do shimmer de 0 a 1 de forma infinita
@Composable
fun rememberShimmerState(): ShimmerState {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    return ShimmerState(progress)
}

/**
 * Modificador que aplica o efeito shimmer.
 *
 * @param enabled Flag para ativar ou desativar o shimmer.
 * @param defaultBackground Cor de fundo padrão a ser desenhada caso o componente não defina um.
 * @param shimmerColors Lista de cores que serão usadas para o gradiente do shimmer.
 *        O padrão é [Transparent, White (0.5f alpha), Transparent].
 * @param gradientWidthFactor Fator multiplicador da largura do gradiente em relação à largura do componente.
 */
fun Modifier.shimmer(
    enabled: Boolean = true,
    defaultBackground: Color = Color.LightGray,
    shimmerColors: List<Color> = listOf(
        Color.Transparent,
        Color.White.copy(alpha = 0.5f),
        Color.Transparent
    ),
    gradientWidthFactor: Float = 1.5f
): Modifier = composed {
    if (!enabled) return@composed this

    // Estado para armazenar a posição global do componente
    var globalOffset by remember { mutableStateOf(Offset.Zero) }
    // Estado animado do shimmer
    val shimmerState = rememberShimmerState()

    this
        // Captura a posição global do componente para ajustar o gradiente corretamente
        .onGloballyPositioned { coordinates ->
            globalOffset = coordinates.positionInRoot()
        }
        // Intercepta o desenho para aplicar o fundo e o efeito shimmer
        .drawWithContent {
            // Primeiro, desenha um fundo padrão para garantir que o efeito seja visível
            drawRect(color = defaultBackground)

            // Calcula a largura do gradiente baseada no fator informado
            val gradientWidth = size.width * gradientWidthFactor
            // Calcula a posição horizontal de início do gradiente com base no progresso animado
            val startX = -gradientWidth + (size.width + gradientWidth) * shimmerState.progress
            // Cria um gradiente linear utilizando as cores parametrizadas
            val brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(startX - globalOffset.x, 0f - globalOffset.y),
                end = Offset(startX + gradientWidth - globalOffset.x, size.height - globalOffset.y)
            )
            // Desenha o retângulo com o gradiente sobre o fundo, utilizando blend mode para sobreposição
            drawRect(brush = brush, blendMode = BlendMode.SrcOver)
        }
}



@Composable
fun ShimmerTextExample() {
    var shimmerEnabled by remember { mutableStateOf(true) }

    BasicText(
        text = "Texto com Shimmer",
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Green)
            .clickable { shimmerEnabled = !shimmerEnabled }
            .shimmer(enabled = shimmerEnabled)
    )
}

@Composable
fun ShmmerImageExample() {
    var shimmerEnabled by remember { mutableStateOf(true) }

    Image(
        painter = painterResource(com.example.studytests.R.drawable.ic_launcher_foreground),
        contentDescription = null,
        modifier = Modifier
            .background(Color.LightGray)
            .size(22.dp)
            .clickable { shimmerEnabled = !shimmerEnabled }
            .shimmer(shimmerEnabled)
    )
}