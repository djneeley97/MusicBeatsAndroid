package com.musicbeats.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


object BeatsColors {
    val Purple = Color(0xFFAF52DE)
    val Orange = Color(0xFFFF9500)
    val Gray = Color(0xFFFFFFFF)

    val Red = Color(0xFFFF3B30)
    val Green = Color(0xFF34C759)
    val Blue = Color(0xFF007AFF)
    val CardBackground = Gray.copy(alpha = 0.15f)
    val SheetBackground = Color(0xFF1C1C1E)
}


fun hexColor(hex: String): Color = Color(android.graphics.Color.parseColor(hex))


object IosType {
    val largeTitle = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold)
    val title2 = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
    val title3Bold = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
    val headline = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    val body = TextStyle(fontSize = 17.sp)
    val subheadline = TextStyle(fontSize = 15.sp)
    val caption = TextStyle(fontSize = 12.sp)
}

private val DarkColors = darkColorScheme(
    primary = BeatsColors.Purple,
    onPrimary = Color.White,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

/** Same as .preferredColorScheme(.dark) on every screen. */
@Composable
fun MusicBeatsTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColors) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black,
            contentColor = Color.White
        ) {
            content()
        }
    }
}
