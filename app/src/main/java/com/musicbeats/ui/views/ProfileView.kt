package com.musicbeats.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.musicbeats.data.ProfileService
import com.musicbeats.ui.components.AssetImage
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType

@Composable
fun ProfileView(onOpenSettings: () -> Unit) {
    // Updates automatically when a new avatar/header is uploaded in Settings
    val profile by ProfileService.profile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section (goes under the status bar, like .edgesIgnoringSafeArea(.top))
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            // Uploaded header if there is one, otherwise the default picture
            val headerURL = profile.headerURL
            if (headerURL != null) {
                AsyncImage(
                    model = headerURL,
                    contentDescription = "Header",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                AssetImage("gr86s", Modifier.fillMaxSize())
            }

            // Settings gear (NavigationLink -> SettingsView)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable(onClick = onOpenSettings)
                    .padding(16.dp)
            ) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = BeatsColors.Purple)
            }

            // Avatar: uploaded one if there is one, otherwise the default picture
            val avatarModifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp)
                .offset(y = 120.dp)
                .size(80.dp)
                .shadow(5.dp, CircleShape)
                .clip(CircleShape)
                .border(3.dp, Color.White, CircleShape)

            val avatarURL = profile.avatarURL
            if (avatarURL != null) {
                AsyncImage(
                    model = avatarURL,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = avatarModifier
                )
            } else {
                AssetImage(name = "gr86bucees", modifier = avatarModifier)
            }
        }

        // Bio
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Bio", style = IosType.headline, color = BeatsColors.Purple, modifier = Modifier.padding(horizontal = 16.dp))
            Text(
                "Music lover. Creating vibes and discovering new sounds every day.",
                style = IosType.subheadline,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Location Section
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Location", style = IosType.headline, color = BeatsColors.Purple, modifier = Modifier.padding(horizontal = 16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(Icons.Filled.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Text("Dayton, Ohio", style = IosType.subheadline, color = BeatsColors.Gray)
            }
        }

        // Favorite Tracks Section
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Favorite Tracks", style = IosType.headline, color = BeatsColors.Purple, modifier = Modifier.padding(horizontal = 16.dp))
            repeat(3) { index -> SongRow("Favorite Song ${index + 1}", "Artist ${index + 1}") }
        }

        // Recently Played Section
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Recently Played", style = IosType.headline, color = BeatsColors.Purple, modifier = Modifier.padding(horizontal = 16.dp))
            repeat(3) { index -> SongRow("Recent Song ${index + 1}", "Artist ${index + 1}") }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun SongRow(title: String, artist: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BeatsColors.Gray.copy(alpha = 0.3f))
        )
        Column {
            Text(title, style = IosType.subheadline, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(artist, style = IosType.caption, color = BeatsColors.Purple,)
        }
    }
}

@Preview
@Composable
private fun ProfileViewPreview() {
    ProfileView(onOpenSettings = {})
}
