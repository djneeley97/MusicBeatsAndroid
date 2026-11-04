package com.musicbeats.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType


@Composable
fun TabBarButton(
    icon: ImageVector,
    title: String,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = selectedTab == title.lowercase()
    val color = if (isSelected) BeatsColors.Purple else BeatsColors.Gray

    Column(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onTabSelected(title.lowercase()) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(22.dp))
        Text(title, color = color, style = IosType.caption)
    }
}
