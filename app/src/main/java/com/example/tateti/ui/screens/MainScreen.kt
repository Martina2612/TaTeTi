package com.example.tateti.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tateti.R

@Composable
fun MainScreen(onStartGame: (String, String) -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A1B9A), Color(0xFF4A148C))
    )
    var playerName by remember { mutableStateOf("") }
    var selectedSymbol by remember { mutableStateOf("X") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            // Logo: Asegurate de tener R.drawable.tic_tac_toe en res/drawable
            Image(
                painter = painterResource(id = R.drawable.tic_tac_toe),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ta | Te | Ti",
                fontSize = 64.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFFD700)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Ingresa tu nombre", color = Color(0xFFD3D3D3)) },
                modifier = Modifier.width(300.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de selección: Cruz
                Image(
                    painter = painterResource(id = R.drawable.cruz_image),
                    contentDescription = "Cruz",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { selectedSymbol = "X" }
                        .alpha(if (selectedSymbol == "X") 1f else 0.5f)
                )
                // Botón de selección: Círculo
                Image(
                    painter = painterResource(id = R.drawable.circulo_image),
                    contentDescription = "Círculo",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { selectedSymbol = "O" }
                        .alpha(if (selectedSymbol == "O") 1f else 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    val name = if (playerName.isBlank()) "Extraño" else playerName
                    onStartGame(name, selectedSymbol)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD740)),
                modifier = Modifier
                    .width(280.dp)
                    .height(64.dp)
            ) {
                Text(
                    text = "¡Vamos a jugar!",
                    color = Color(0xFF663399),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
