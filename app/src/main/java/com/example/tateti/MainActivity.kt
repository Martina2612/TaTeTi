package com.example.tateti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.tateti.navigation.Screen
import com.example.tateti.ui.screens.GameScreen
import com.example.tateti.ui.screens.MainScreen
import com.example.tateti.ui.screens.ResultScreen
import com.example.tateti.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }

    when (val screen = currentScreen) {
        is Screen.Main -> {
            MainScreen { name, symbol ->
                currentScreen = Screen.Game(name, symbol)
            }
        }
        is Screen.Game -> {
            GameScreen(
                playerName = screen.playerName,
                playerSymbol = screen.playerSymbol,
                onNewGame = { currentScreen = Screen.Main },
                onGameEnd = { resultMessage ->
                    currentScreen = Screen.Result(
                        resultMessage = resultMessage,
                        playerName = screen.playerName,
                        playerSymbol = screen.playerSymbol
                    )
                }

            )
        }
        is Screen.Result -> {
            ResultScreen(
                resultMessage = screen.resultMessage,
                onPlayAgain = {
                    currentScreen = Screen.Game(
                        playerName = screen.playerName,
                        playerSymbol = screen.playerSymbol
                    )
                },
                onGoHome = {
                    currentScreen = Screen.Main
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TicTacToeTheme {
        MainScreen(onStartGame = { _, _ -> })
    }
}
