package com.example.studytests.components.calendar.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.lazyListVerticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp,
    showScrollBarTrack: Boolean = true,
    scrollBarTrackColor: Color = Color.Gray,
    scrollBarColor: Color = Color.Black,
    scrollBarCornerRadius: Float = 4f,
    endPadding: Float = 12f
): Modifier {
    return drawWithContent {
        drawContent()

        val layoutHeight = size.height

        val totalItems = state.layoutInfo.totalItemsCount
        val visibleItems = state.layoutInfo.visibleItemsInfo

        if (visibleItems.isEmpty()) return@drawWithContent

        val firstVisibleItem = visibleItems.first()
        val averageItemHeight = visibleItems.sumOf { it.size } / visibleItems.size.toFloat()

        val totalHeight = totalItems * averageItemHeight
        val scrollbarHeight = (layoutHeight / totalHeight) * layoutHeight

        val scrollOffset = (firstVisibleItem.index * averageItemHeight - firstVisibleItem.offset) / totalHeight
        val scrollbarOffset = scrollOffset * layoutHeight

        if (showScrollBarTrack) {
            drawRoundRect(
                color = scrollBarTrackColor,
                cornerRadius = CornerRadius(scrollBarCornerRadius),
                topLeft = Offset(x = size.width - endPadding, y = 0f),
                size = Size(width.toPx(), layoutHeight)
            )
        }

        drawRoundRect(
            color = scrollBarColor,
            cornerRadius = CornerRadius(scrollBarCornerRadius),
            topLeft = Offset(x = size.width - endPadding, y = scrollbarOffset),
            size = Size(width.toPx(), scrollbarHeight)
        )
    }
}
