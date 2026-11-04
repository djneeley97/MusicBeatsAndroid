package com.musicbeats.ui.views

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.data.Song
import com.musicbeats.data.SongService
import com.musicbeats.ui.components.BeatsTopBar
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType

@Composable
fun SearchResults(
    searchTerm: String,
    onSongClick: (Song) -> Unit,
    onBack: () -> Unit
) {
    var songs by remember { mutableStateOf<List<Song>>(emptyList()) }
    val songService = remember { SongService() }

    // .task { await performSearch() }
    LaunchedEffect(searchTerm) {
        try {
            songs = songService.searchSongs(searchTerm)
            Log.d("SearchResults", songs.toString())
        } catch (e: Exception) {
            Log.e("SearchResults", "error searching for: $searchTerm", e)
        }
    }

    Column(Modifier.fillMaxSize()) {
        BeatsTopBar("Results for: $searchTerm", onBack = onBack)

        LazyColumn {
            items(songs, key = { it.id }) { song ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSongClick(song) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(song.title, style = IosType.body, modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = BeatsColors.Gray)
                }
                HorizontalDivider(color = BeatsColors.Gray.copy(alpha = 0.3f), modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun SearchResultsPreview() {
    SearchResults(searchTerm = "beats", onSongClick = {}, onBack = {})
}
