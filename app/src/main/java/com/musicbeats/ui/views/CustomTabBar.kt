package com.musicbeats.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.theme.BeatsColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTabBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showUpload by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center // ZStack
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabBarButton(Icons.Outlined.Home, "Home", selectedTab, onTabSelected)
            Spacer(Modifier.width(30.dp))
            TabBarButton(Icons.Outlined.Search, "Search", selectedTab, onTabSelected)
            Spacer(Modifier.width(100.dp))
            TabBarButton(Icons.Outlined.FavoriteBorder, "Favorites", selectedTab, onTabSelected)
            Spacer(Modifier.width(20.dp))
            TabBarButton(Icons.Outlined.Person, "Profile", selectedTab, onTabSelected)
        }

        // Floating Button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(70.dp)
                .clip(CircleShape)
                .background(BeatsColors.Purple)
                .clickable { showUpload = true },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Upload", tint = Color.White, modifier = Modifier.size(32.dp))
        }
    }

    // .sheet(isPresented: $showUpload) { UploadView() }
    if (showUpload) {
        ModalBottomSheet(
            onDismissRequest = { showUpload = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = BeatsColors.SheetBackground
        ) {
            UploadView()
        }
    }
}

@Preview
@Composable
private fun CustomTabBarPreview() {
    var selected by remember { mutableStateOf("home") }
    CustomTabBar(selectedTab = selected, onTabSelected = { selected = it })
}
