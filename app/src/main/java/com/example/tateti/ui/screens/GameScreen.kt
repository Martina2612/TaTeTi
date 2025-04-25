package com.example.tateti.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tateti.R
import com.example.tateti.logic.*
import com.example.tateti.ui.components.GameBoard
import com.example.tateti.ui.components.TimerProgressBar
import com.example.tateti.ui.theme.*
import com.tuapp.ui.components.FluorButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun GameScreen(
    playerName: String,
    playerSymbol: String,
    onNewGame: () -> Unit,
    onGameEnd: (String) -> Unit
) {
    val context = LocalContext.current
    val aiSymbol = if (playerSymbol == "X") "O" else "X"
    val board = remember { mutableStateListOf(*Array(9) { "" }) }
    var infoText by remember { mutableStateOf("$playerName, juegas con $playerSymbol") }
    var resultText by remember { mutableStateOf("") }
    var playerTurn by remember { mutableStateOf(true) }
    var moves by remember { mutableStateOf(0) }
    var timer by remember { mutableStateOf(10) }
    var winningCombo by remember { mutableStateOf<List<Int>?>(null) }
    val coroutineScope = rememberCoroutineScope()

    fun playSound(resId: Int) {
        MediaPlayer.create(context, resId)?.apply {
            start()
            setOnCompletionListener { release() }
        }
    }

    LaunchedEffect(resultText) {
        if (resultText.isNotEmpty()) {
            val sound = when {
                resultText.contains(playerName, true) -> R.raw.win_sound
                resultText.contains("máquina", true) -> R.raw.lose_sound
                resultText.contains("empate", true) -> R.raw.draw_sound
                else -> null
            }
            sound?.let { playSound(it) }
        }
    }

    fun evaluarFin(turnoJugador: Boolean, mensaje: String) {
        coroutineScope.launch {
            delay(1000)
            resultText = mensaje
            onGameEnd(mensaje)
        }
    }

    fun resetGame() {
        for (i in board.indices) board[i] = ""
        infoText = "$playerName, juegas con $playerSymbol"
        resultText = ""
        playerTurn = true
        moves = 0
        timer = 10
        winningCombo = null
    }

    fun computerMove() {
        val empty = board.withIndex().filter { it.value.isEmpty() }.map { it.index }
        if (moves == 0) {
            val pos = if (board[4].isEmpty()) 4 else 0
            board[pos] = aiSymbol
        } else if (Random.nextFloat() < 0.4f) {
            board[empty.random()] = aiSymbol
        } else {
            val scores = empty.map { i ->
                val temp = board.toMutableList().also { it[i] = aiSymbol }
                i to minimax(temp, aiSymbol, playerSymbol, 0, false, Int.MIN_VALUE, Int.MAX_VALUE)
            }
            val best = scores.maxByOrNull { it.second }?.first
            if (best != null) board[best] = aiSymbol
        }

        moves++
        winningCombo = getWinningCombo(board, aiSymbol)
        when {
            winningCombo != null -> evaluarFin(false, "La máquina gana!")
            isDraw(board)     -> evaluarFin(false, "Empate!")
            else              -> {
                playerTurn = true
                infoText = "$playerName, tu turno"
                timer = 10
            }
        }
    }

    fun forceMove() {
        val idx = board.indexOfFirst { it.isEmpty() }
        if (idx >= 0) {
            board[idx] = playerSymbol
            moves++
            winningCombo = getWinningCombo(board, playerSymbol)
            when {
                winningCombo != null -> evaluarFin(true, "$playerName gana!")
                isDraw(board)        -> evaluarFin(true, "Empate!")
                else -> {
                    playerTurn = false
                    infoText = "Turno de la máquina"
                    coroutineScope.launch {
                        delay(500)
                        computerMove()
                    }
                }
            }
        }
    }

    LaunchedEffect(playerTurn, resultText) {
        if (playerTurn && resultText.isEmpty()) {
            timer = 10
            while (timer > 0) {
                delay(1000)
                timer--
                if (!playerTurn || resultText.isNotEmpty()) break
            }
            if (timer == 0 && playerTurn && resultText.isEmpty()) forceMove()
        }
    }

    fun onCellClick(index: Int) {
        if (board[index].isNotEmpty() || !playerTurn) return
        board[index] = playerSymbol
        playSound(R.raw.click_sound)
        moves++
        winningCombo = getWinningCombo(board, playerSymbol)
        when {
            winningCombo != null -> evaluarFin(true, "Wow, $playerName gana!")
            isDraw(board)        -> evaluarFin(true, "Empate!")
            else -> {
                playerTurn = false
                infoText = "Turno de la máquina"
                coroutineScope.launch {
                    delay(500)
                    computerMove()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(MoradoOscuro, MoradoIntermedio, MoradoClaro))
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(50.dp))
            Text(
                text = "Ta | Te | Ti",
                style = TextStyle(
                    brush = Brush.verticalGradient(listOf(Color.Cyan, Color.Magenta, Color.Yellow)),
                    fontFamily = BungeeSpiceFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 55.sp
                )
            )
            Spacer(Modifier.height(24.dp))
            TimerProgressBar(timeLeft = timer, maxTime = 10)
            Spacer(Modifier.height(24.dp))
            Text(
                text = infoText,
                fontSize = 22.sp,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(20.dp))

            GameBoard(
                board = board,
                onCellClick = ::onCellClick,
                winningCombo = winningCombo,
                resultText = resultText
            )

            Spacer(Modifier.height(10.dp))
            if (resultText.isNotEmpty()) {
                Text(
                    text = resultText,
                    fontSize = 22.sp,
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(Modifier.height(15.dp))
            FluorButton(
                text = "NUEVO JUEGO",
                onClick = ::resetGame
            )
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onNewGame,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(
                    text = "Volver al menú",
                    fontSize = 16.sp,
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    TicTacToeTheme {
        GameScreen(
            playerName = "Claudio",
            playerSymbol = "X",
            onNewGame = {},
            onGameEnd = {}
        )
    }
}