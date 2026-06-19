package com.lumina.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CyberBlack = Color(0xFF0A0A0C)
val CyberPurple = Color(0xFF8A2BE2)
val NeonCyan = Color(0xFF00FFFF)
val DarkGray = Color(0xFF1A1A1F)
val TextWhite = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
    primary = CyberPurple,
    secondary = NeonCyan,
    background = CyberBlack,
    surface = DarkGray,
    onPrimary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun LuminaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
