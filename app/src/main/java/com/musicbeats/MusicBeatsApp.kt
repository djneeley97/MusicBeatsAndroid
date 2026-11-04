package com.musicbeats

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musicbeats.data.AuthService
import com.musicbeats.data.Song
import com.musicbeats.ui.views.*


@Composable
fun MusicBeatsApp(authService: AuthService) {
    val user by authService.currentUser.collectAsState()

    if (user == null) {
        val nav = rememberNavController()
        NavHost(navController = nav, startDestination = "login") {
            composable("login") {
                FirebaseLoginView(
                    onLogin = authService::signIn,
                    onShowSignup = { nav.navigate("signup") }
                )
            }
            composable("signup") {
                FirebaseSignupView(
                    onSignup = authService::createAccount,
                    onBack = { nav.popBackStack() }
                )
            }
        }
    } else {
        val nav = rememberNavController()
        NavHost(navController = nav, startDestination = "main") {
            composable("main") {
                FirebaseHomeView(
                    onOpenSettings = { nav.navigate("settings") },
                    onSearch = { term -> nav.navigate("results/${Uri.encode(term)}") }
                )
            }
            composable("settings") {
                SettingsView(
                    email = user?.email ?: "user@email.com",
                    onSignOut = { authService.signOut() },
                    onBack = { nav.popBackStack() }
                )
            }
            composable("results/{term}") { entry ->
                SearchResults(
                    searchTerm = entry.arguments?.getString("term").orEmpty(),
                    onSongClick = { song -> nav.navigate("song/${Uri.encode(song.title)}") },
                    onBack = { nav.popBackStack() }
                )
            }
            composable("song/{title}") { entry ->
                SongDetailView(
                    song = Song(title = entry.arguments?.getString("title").orEmpty()),
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}
