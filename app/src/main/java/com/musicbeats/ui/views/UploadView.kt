package com.musicbeats.ui.views

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.BeatsTopBar
import com.musicbeats.ui.theme.BeatsColors


@Composable
fun UploadView() {
    val context = LocalContext.current

    var artworkImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var title by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    val tags = remember { mutableStateListOf<String>() }
    var newTag by remember { mutableStateOf("") }
    var audioUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            artworkImage = context.contentResolver.openInputStream(uri)?.use {
                BitmapFactory.decodeStream(it)
            }?.asImageBitmap()
        }
    }
    val audioPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) audioUri = uri else Log.d("UploadView", "Audio selection cancelled")
    }

    fun uploadSong() {
        Log.d("UploadView", "Uploading song...")
        Log.d("UploadView", "Title: $title")
        Log.d("UploadView", "Artist: $artist")
        Log.d("UploadView", "Tags: $tags")
        Log.d("UploadView", "Audio URL: $audioUri")
    }

    Column(Modifier.fillMaxWidth()) {
        BeatsTopBar("Upload Audio to Music Beats", applyStatusBarPadding = false)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Artwork
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BeatsColors.Purple.copy(alpha = 0.9f))
                    .clickable {
                        imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                val image = artworkImage
                if (image != null) {
                    Image(image, contentDescription = "Artwork", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                } else {
                    Text("Upload Artwork", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Title
            BeatsOutlinedField(title, { title = it }, "Song Title", Modifier.padding(horizontal = 16.dp).fillMaxWidth())

            // Artist / Producer
            BeatsOutlinedField(artist, { artist = it }, "Artist / Producer", Modifier.padding(horizontal = 16.dp).fillMaxWidth())

            // Tags
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    BeatsOutlinedField(newTag, { newTag = it }, "Add tag", Modifier.weight(1f))
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(BeatsColors.Purple)
                            .clickable {
                                if (newTag.isNotEmpty()) {
                                    tags.add(newTag)
                                    newTag = ""
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text("Add", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                WrapView(tags) { tag ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BeatsColors.Purple.copy(alpha = 0.2f))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(tag, modifier = Modifier.weight(1f, fill = false), maxLines = 1)
                        Icon(
                            Icons.Filled.Cancel,
                            contentDescription = "Remove $tag",
                            tint = BeatsColors.Purple,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { tags.remove(tag) }
                        )
                    }
                }
            }

            // Audio Upload
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (audioUri == null) BeatsColors.Red.copy(alpha = 0.7f) else BeatsColors.Green.copy(alpha = 0.7f))
                    .clickable { audioPicker.launch("audio/*") }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.MusicNote, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(if (audioUri == null) "Upload Audio File" else "Audio Selected", fontWeight = FontWeight.Bold, color = Color.White)
            }

            // Submit
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BeatsColors.Purple)
                    .clickable { uploadSong() }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Upload Song", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
private fun BeatsOutlinedField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        shape = RoundedCornerShape(6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BeatsColors.Purple,
            unfocusedBorderColor = BeatsColors.Gray,
            cursorColor = BeatsColors.Purple
        ),
        modifier = modifier
    )
}

/** Swift's WrapView: lays items out 3 per row. */
@Composable
fun <T> WrapView(items: List<T>, content: @Composable (T) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { item -> Box(Modifier.weight(1f)) { content(item) } }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Preview
@Composable
private fun UploadViewPreview() {
    UploadView()
}
