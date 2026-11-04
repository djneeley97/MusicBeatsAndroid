package com.musicbeats.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.musicbeats.data.Song
import com.musicbeats.ui.components.BeatsTopBar
import com.musicbeats.ui.theme.IosType


@Composable
fun SongDetailView(song: Song, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        BeatsTopBar("", onBack = onBack)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(song.title, style = IosType.body)
        }
    }
}
