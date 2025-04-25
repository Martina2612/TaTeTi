package com.example.tateti.navigation

sealed class Screen {
    object Main : Screen()
    data class Game(val playerName: String, val playerSymbol: String) : Screen()
    data class Result(val resultMessage: String, val playerName: String, val playerSymbol: String) : Screen()
}
