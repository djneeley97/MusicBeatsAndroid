package com.musicbeats.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.BeatsTextField
import com.musicbeats.ui.theme.BeatsColors
import com.musicbeats.ui.theme.IosType

private val trending = listOf("Hip Hop", "R&B", "Drill", "Trap", "Lo-fi", "Afrobeats")


@Composable
fun SearchView(
    onOpenSettings: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var selectedTab by rememberSaveable { mutableStateOf("home") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(Modifier.weight(1f)) {
            when (selectedTab) {
                "search" -> Column(Modifier.statusBarsPadding()) {
                    // Search Bar
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(BeatsColors.Gray.copy(alpha = 0.2f))
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = null, tint = BeatsColors.Purple)
                        BeatsTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = "Search artists, beats...",
                            background = Color.Transparent,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                if (searchText.isNotBlank()) onSearch(searchText.trim())
                            }),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        // Trending Section
                        item { Text("Trending Tags", color = Color.White, style = IosType.title2) }
                        item {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(trending) { tag ->
                                    Text(
                                        tag,
                                        color = Color.White,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(BeatsColors.Purple)
                                            .padding(horizontal = 16.dp, vertical = 10.dp)
                                    )
                                }
                            }
                        }

                        // Recommended Beats
                        item { Text("Recommended Beats", color = Color.White, style = IosType.title2) }
                        items(10) { BeatCard() }
                    }
                }

                "home" -> HomePage()
                "favorites" -> FavoritesView()
                "profile" -> ProfileView(onOpenSettings = onOpenSettings)
            }
        }

        // Bottom Navigation Bar
        CustomTabBar(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
    }
}

@Preview
@Composable
private fun SearchViewPreview() {
    SearchView()
}
