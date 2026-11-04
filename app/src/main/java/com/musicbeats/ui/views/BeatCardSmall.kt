package com.musicbeats.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musicbeats.ui.components.AssetImage
import com.musicbeats.ui.theme.BeatsColors

@Composable
fun BeatCardSmall(
    imageName: String,
    title: String,
    artist: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(160.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Album Art
        Box(
            Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            AssetImage(imageName, Modifier.fillMaxSize())

            // Subtle gradient overlay
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.5f to Color.Black.copy(alpha = 0f),
                            1.0f to Color.Black.copy(alpha = 0.4f)
                        )
                    )
            )
        }

        // Song Title
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Artist Name
        Text(text = artist, color = BeatsColors.Gray, fontSize = 12.sp)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BeatCardSmallPreview() {
    BeatCardSmall("migos", "Computers (Freestyle)", "Migos")
}
