package com.musicbeats.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicbeats.ui.components.BeatsTopBar

private val hipHop = listOf(
    Triple("migos", "Computers (Freestyle)", "Migos"),
    Triple("drake", "Nonstop", "Drake"),
    Triple("kendrick", "HUMBLE.", "Kendrick Lamar")
)
private val trap = listOf(
    Triple("future", "Mask Off", "Future"),
    Triple("travis", "SICKO MODE", "Travis Scott"),
    Triple("youngthug", "Hot", "Young Thug")
)
private val rnb = listOf(
    Triple("weeknd", "Blinding Lights", "The Weeknd"),
    Triple("sza", "Kill Bill", "SZA"),
    Triple("brent", "Trust", "Brent Faiyaz")
)
private val favorites = listOf(
    Triple("jcole", "No Role Modelz", "J. Cole"),
    Triple("uzi", "XO TOUR Llif3", "Lil Uzi Vert")
)
private val history = listOf(
    Triple("babykeem", "family ties", "Baby Keem"),
    Triple("don", "After Party", "Don Toliver")
)

@Composable
fun HomePage() {
    Column(Modifier.fillMaxSize()) {
        BeatsTopBar("Welcome to Music Beats")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            SectionView("Trending in Hip-Hop", hipHop)
            SectionView("Trending in Trap", trap)
            SectionView("Trending in R&B", rnb)
            SectionView("Favorites", favorites)
            SectionView("Listening History", history)
            // room so content isn't hidden behind the floating upload button
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
private fun HomePagePreview() {
    HomePage()
}
