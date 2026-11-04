package com.musicbeats.ui.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.musicbeats.ui.theme.BeatsColors


@Composable
fun HomeView() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("MusicBeats", Context.MODE_PRIVATE) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BeatsColors.Purple, Color.Black)))
    ) {
        IconButton(
            onClick = { prefs.edit().putBoolean("isLoggedIn", false).apply() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "logout", tint = Color.White)
        }
    }
}

@Preview
@Composable
private fun HomeViewPreview() {
    HomeView()
}
