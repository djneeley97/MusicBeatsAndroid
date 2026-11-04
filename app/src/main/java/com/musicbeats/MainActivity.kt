package com.musicbeats

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.musicbeats.data.AuthService
import com.musicbeats.ui.theme.MusicBeatsTheme

class MainActivity : ComponentActivity() {

    private val authService by lazy { AuthService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            MusicBeatsTheme {
                MusicBeatsApp(authService)
            }
        }
    }
}
