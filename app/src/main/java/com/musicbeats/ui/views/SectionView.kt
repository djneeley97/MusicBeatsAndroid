package com.musicbeats.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.AssetImage
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType


@Composable
fun SectionView(title: String, entries: List<Triple<String, String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, style = IosType.title3Bold, color = Color.White)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(entries, key = { it.second }) { item ->
                SectionItemCard(imageName = item.first, title = item.second, subtitle = item.third)
            }
        }
    }
}

@Composable
private fun SectionItemCard(imageName: String, title: String, subtitle: String) {
    Column(
        modifier = Modifier.width(140.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Artwork: purple until you add a matching image to res/drawable
        AssetImage(
            name = imageName,
            placeholder = BeatsColors.Purple,
            alpha = 0.9f,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Text(title, color = Color.White, style = IosType.headline, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(subtitle, color = BeatsColors.Gray, style = IosType.subheadline, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}
