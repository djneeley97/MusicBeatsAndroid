package com.musicbeats.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType


@Composable
fun BeatCard(
    modifier: Modifier = Modifier,
    title: String = "Trap Beat - Dark Vibes",
    producer: String = "Producer Name"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BeatsColors.CardBackground)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BeatsColors.Gray)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(title, color = Color.White, style = IosType.headline)
            Text(producer, color = BeatsColors.Gray, style = IosType.subheadline)
        }

        Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = BeatsColors.Red, modifier = Modifier.size(26.dp))
        Icon(Icons.Filled.PlayCircle, contentDescription = "Play", tint = BeatsColors.Purple, modifier = Modifier.size(28.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BeatCardPreview() {
    BeatCard()
}
