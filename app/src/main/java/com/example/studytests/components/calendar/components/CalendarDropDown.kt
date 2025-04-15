package com.example.studytests.components.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.studytests.components.calendar.data.CalendarItemData

@Composable
internal fun CalendarDropDown(
    selectedItem: CalendarItemData?,
    items: List<CalendarItemData>,
    modifier: Modifier = Modifier,
    placeholder: String = "Selecionar",
    onItemSelected: (CalendarItemData) -> Unit
) {
    val density = LocalDensity.current

    var expanded by remember { mutableStateOf(false) }

    var boxHeightPx by remember { mutableIntStateOf(0) }
    var dividerHeightPx by remember { mutableIntStateOf(0) }
    var rowWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .wrapContentWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(48.dp)
                .onGloballyPositioned {
                    boxHeightPx = it.size.height
                }
                .clickable { expanded = true },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp)
                    .onGloballyPositioned {
                        rowWidth = it.size.width
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = selectedItem?.displayValue ?: placeholder,
                    style = TextStyle(fontSize = 14.sp, color = Color.Black)
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        HorizontalDivider(
            color = if (expanded) Color.Blue else Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .width(with(density) { rowWidth.toDp() + 16.dp })
                .onGloballyPositioned {
                    dividerHeightPx = it.size.height
                }
        )

        if (expanded) {
            val totalYOffSet = boxHeightPx + dividerHeightPx

            Popup(
                onDismissRequest = { expanded = false },
                offset = IntOffset(0, totalYOffSet),
                properties = PopupProperties(focusable = true, excludeFromSystemGesture = true)
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(max = 224.dp)
                        .background(Color.White)
                        .width(with(density) { rowWidth.toDp() + 16.dp })
                ) {
                    val lazyListState = rememberLazyListState()

                    LazyColumn(
                        userScrollEnabled = true,
                        state = lazyListState,
                        modifier = Modifier.lazyListVerticalScrollbar(lazyListState)
                    ) {
                        items(items.size) { index ->
                            val itemCalendar = items[index]

                            Row(
                                modifier = Modifier
                                    .height(48.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                VerticalDivider(
                                    modifier = Modifier.fillMaxHeight(),
                                    color = if (
                                        expanded
                                        && selectedItem != null
                                        && selectedItem.displayValue == itemCalendar.displayValue
                                    ) {
                                        Color.Blue
                                    } else {
                                        Color.Transparent
                                    },
                                    thickness = 2.dp
                                )
                                Text(
                                    text = itemCalendar.displayValue,
                                    modifier = Modifier
                                        .clickable {
                                            onItemSelected(itemCalendar)
                                            expanded = false
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
