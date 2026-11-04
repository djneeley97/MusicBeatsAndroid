package com.musicbeats.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.BeatsTopBar
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType

@Composable
fun SettingsView(
    email: String = "user@email.com",
    onSignOut: () -> Unit,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        BeatsTopBar("", onBack = onBack)

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings", style = IosType.largeTitle)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(BeatsColors.Gray.copy(alpha = 0.1f))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Email", style = IosType.headline)
                Text(email, color = BeatsColors.Gray)
            }

            SettingsButton("Change Password", BeatsColors.Purple) {
                Log.d("SettingsView", "Change Password tapped")
            }
            SettingsButton("Delete Account", BeatsColors.Red) {
                Log.d("SettingsView", "Delete Account tapped")
            }
            SettingsButton("Sign Out", BeatsColors.Purple) {
                try {
                    onSignOut()
                } catch (e: Exception) {
                    Log.e("SettingsView", "error signing out: ${e.localizedMessage}")
                }
            }
        }
    }
}

@Composable
private fun SettingsButton(text: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, style = IosType.body)
    }
}


@Composable
fun LoginView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = IosType.largeTitle)
        Box(
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(BeatsColors.Blue)
                .clickable { /* appState.isLoggedIn = true */ }
                .padding(16.dp)
        ) {
            Text("Sign In", color = Color.White)
        }
    }
}

@Preview
@Composable
private fun SettingsViewPreview() {
    SettingsView(onSignOut = {}, onBack = {})
}
