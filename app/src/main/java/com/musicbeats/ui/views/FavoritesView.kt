package com.musicbeats.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.data.Beat
import com.musicbeats.ui.components.BeatsTopBar
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType

@Composable
fun FavoritesView() {
    val favorites = remember {
        mutableStateListOf(
            Beat(title = "Trap Beat - Dark Vibes", artist = "Producer One", isFavorite = true),
            Beat(title = "Lo-fi Chill", artist = "BeatSmith", isFavorite = true),
            Beat(title = "Afro Groove", artist = "DJ Kola", isFavorite = true)
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        BeatsTopBar("Favorited Tracks")

        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = null, tint = BeatsColors.Gray, modifier = Modifier.size(42.dp))
                Spacer(Modifier.height(12.dp))
                Text("No favorites yet", color = BeatsColors.Gray, style = IosType.headline)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(favorites, key = { it.id }) { beat ->
                    BeatRow(beat) { toggled ->
                        // if heart is unfavorited, remove from list
                        val idx = favorites.indexOfFirst { it.id == toggled.id }
                        if (idx != -1) {
                            if (!toggled.isFavorite) favorites.removeAt(idx) else favorites[idx] = toggled
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BeatRow(beat: Beat, onChange: (Beat) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BeatsColors.CardBackground)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Thumbnail placeholder
        Box(
            Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BeatsColors.Gray)
        )

        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(beat.title, color = Color.White, style = IosType.headline)
            Text(beat.artist, color = BeatsColors.Gray, style = IosType.subheadline)
        }

        // Heart toggle
        IconButton(onClick = { onChange(beat.copy(isFavorite = !beat.isFavorite)) }, modifier = Modifier.size(32.dp)) {
            Icon(
                imageVector = if (beat.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (beat.isFavorite) BeatsColors.Red else BeatsColors.Gray,
                modifier = Modifier.size(26.dp)
            )
        }

        // Play button
        Icon(Icons.Filled.PlayCircle, contentDescription = "Play", tint = BeatsColors.Purple, modifier = Modifier.size(28.dp))
    }
}

@Preview
@Composable
private fun FavoritesViewPreview() {
    FavoritesView()
}
