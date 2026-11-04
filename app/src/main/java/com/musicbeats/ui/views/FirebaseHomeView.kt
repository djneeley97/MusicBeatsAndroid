package com.musicbeats.ui.views

import androidx.compose.runtime.Composable


@Composable
fun FirebaseHomeView(
    onOpenSettings: () -> Unit,
    onSearch: (String) -> Unit
) {
    SearchView(onOpenSettings = onOpenSettings, onSearch = onSearch)
}
