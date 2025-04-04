package com.example.tateti.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tateti.ui.theme.TicTacToeTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    playerName: String,
    playerSymbol: String,
    onNewGame: () -> Unit
) {
    val aiSymbol = if (playerSymbol == "X") "O" else "X"
    // Estado del tablero: Lista mutable de 9 celdas ("", "X" o "O")
    val board = remember { mutableStateListOf(*Array(9) { "" }) }
    var infoText by remember { mutableStateOf("$playerName, juegas con $playerSymbol") }
    var resultText by remember { mutableStateOf("") }
    var playerTurn by remember { mutableStateOf(true) }
    var moves by remember { mutableStateOf(0) }
    // Timer (estático en este ejemplo)
    var timerText by remember { mutableStateOf("0:05") }

    fun resetGame() {
        for (i in board.indices) board[i] = ""
        infoText = "$playerName, juegas con $playerSymbol"
        resultText = ""
        playerTurn = true
        moves = 0
    }

    fun checkWinner(symbol: String): Boolean {
        val combos = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),  // Filas
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),  // Columnas
            listOf(0, 4, 8), listOf(2, 4, 6)                    // Diagonales
        )
        return combos.any { combo -> combo.all { board[it] == symbol } }
    }

    fun isDraw() = board.all { it != "" }

    fun minimax(currentBoard: List<String>, isMaximizing: Boolean): Int {
        if (checkWinner(aiSymbol)) return 1
        if (checkWinner(playerSymbol)) return -1
        if (currentBoard.all { it != "" }) return 0

        return if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (i in currentBoard.indices) {
                if (currentBoard[i] == "") {
                    val newBoard = currentBoard.toMutableList()
                    newBoard[i] = aiSymbol
                    val score = minimax(newBoard, false)
                    bestScore = maxOf(bestScore, score)
                }
            }
            bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in currentBoard.indices) {
                if (currentBoard[i] == "") {
                    val newBoard = currentBoard.toMutableList()
                    newBoard[i] = playerSymbol
                    val score = minimax(newBoard, true)
                    bestScore = minOf(bestScore, score)
                }
            }
            bestScore
        }
    }

    fun computerMove() {
        var bestMove = -1
        var bestScore = Int.MIN_VALUE
        for (i in board.indices) {
            if (board[i] == "") {
                val newBoard = board.toList().toMutableList()
                newBoard[i] = aiSymbol
                val score = minimax(newBoard, false)
                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }
        if (bestMove != -1) {
            board[bestMove] = aiSymbol
            moves++
            if (checkWinner(aiSymbol)) {
                resultText = "La máquina gana!"
            } else if (isDraw()) {
                resultText = "Empate!"
            } else {
                playerTurn = true
                infoText = "$playerName, tu turno"
            }
        }
    }


    val coroutineScope = rememberCoroutineScope()

    fun onCellClick(index: Int) {
        if (board[index] != "" || resultText.isNotEmpty() || !playerTurn) return
        board[index] = playerSymbol
        moves++
        if (checkWinner(playerSymbol)) {
            resultText = "$playerName gana!"
        } else if (isDraw()) {
            resultText = "Empate!"
        } else {
            playerTurn = false
            infoText = "Turno de la máquina"
            // Lanzamos la corrutina en el scope
            coroutineScope.launch {
                delay(500)
                computerMove()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A1B9A), Color(0xFF4A148C))
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ta | Te | Ti",
                fontSize = 64.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFFD700)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Temporizador (estático)
            Text(
                text = timerText,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(14.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = infoText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Tablero 3x3
            Column(
                modifier = Modifier
                    .background(Color(0xFFD3D3D3), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                for (row in 0 until 3) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            Button(
                                onClick = { onCellClick(index) },
                                enabled = board[index] == "" && resultText.isEmpty(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                                modifier = Modifier.size(80.dp)
                            ) {
                                Text(
                                    text = board[index],
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (resultText.isNotEmpty()) {
                Text(
                    text = resultText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { resetGame() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD740)),
                modifier = Modifier.width(180.dp)
            ) {
                Text(
                    text = "NUEVO JUEGO",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF663399)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNewGame() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.width(180.dp)
            ) {
                Text(
                    text = "Volver al menú",
                    fontSize = 16.sp,
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
            playerName = "Jugador",
            playerSymbol = "X",
            onNewGame = {}
        )
    }
}