package com.example.tateti.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun TicTacToeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = MoradoClaro,
            secondary = Amarillo,
            background = Blanco,
            surface = GrisClaro,
            onPrimary = Blanco,
            onSecondary = Blanco,
            onBackground = MoradoOscuro,
            onSurface = MoradoOscuro
        ),
        typography = MyTypography,
        content = content
    )
}
