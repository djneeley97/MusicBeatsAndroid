package com.musicbeats.data

import java.util.UUID


data class Beat(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val artist: String,
    val isFavorite: Boolean
)


data class Song(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val artist: String = ""
)
