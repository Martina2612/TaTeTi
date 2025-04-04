package com.example.tateti.navigation

sealed class Screen {
    object Main : Screen()
    data class Game(val playerName: String, val playerSymbol: String) : Screen()
}