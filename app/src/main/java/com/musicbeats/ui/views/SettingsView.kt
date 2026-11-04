package com.musicbeats.ui.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.data.ProfileService
import com.musicbeats.ui.components.BeatsTopBar
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType
import kotlinx.coroutines.launch

@Composable
fun SettingsView(
    email: String = "user@email.com",
    onSignOut: () -> Unit,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isUploading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var statusIsError by remember { mutableStateOf(false) }

    // Uploads the chosen image and shows how it went
    fun upload(uri: Uri, label: String, action: suspend (Uri) -> String) {
        scope.launch {
            isUploading = true
            statusMessage = "Uploading $label…"
            statusIsError = false
            try {
                action(uri)
                statusMessage = "${label.replaceFirstChar { it.uppercase() }} updated"
            } catch (e: Exception) {
                Log.e("SettingsView", "error uploading $label: ${e.localizedMessage}")
                statusMessage = "Couldn't update $label: ${e.localizedMessage}"
                statusIsError = true
            } finally {
                isUploading = false
            }
        }
    }

    // System photo picker (images only) for the avatar
    val avatarPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) upload(uri, "avatar", ProfileService::uploadAvatar)
    }

    // System photo picker (images only) for the header
    val headerPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) upload(uri, "header", ProfileService::uploadHeader)
    }

    val imagesOnly = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

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

            SettingsButton("Edit Avatar", BeatsColors.Purple, enabled = !isUploading) {
                avatarPicker.launch(imagesOnly)
            }
            SettingsButton("Edit Header", BeatsColors.Purple, enabled = !isUploading) {
                headerPicker.launch(imagesOnly)
            }

            statusMessage?.let {
                Text(
                    it,
                    style = IosType.subheadline,
                    color = if (statusIsError) BeatsColors.Red else BeatsColors.Gray
                )
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
private fun SettingsButton(
    text: String,
    color: Color,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (enabled) color else color.copy(alpha = 0.4f))
            .clickable(enabled = enabled, onClick = onClick)
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
