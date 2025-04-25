package com.example.tateti.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tateti.R
import com.example.tateti.ui.theme.*
import com.example.tateti.ui.theme.QuicksandFontFamily
import com.tuapp.ui.components.FluorButton

@Composable
fun MainScreen(onStartGame: (String, String) -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(MoradoOscuro, MoradoIntermedio, MoradoClaro)
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
            Image(
                painter = painterResource(id = R.drawable.tic_tac_toe),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Ta | Te | Ti",
                style = TextStyle(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF00FFFF),Color(0xFF7C4DFF),Color.Magenta,Color(0xFFFF00FF))
                    ),
                    fontFamily = BungeeSpiceFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 55.sp
                )

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "¿Serás capaz de vencer a la IA?",
                fontFamily = QuicksandFontFamily,
                fontSize = 20.sp,
                color=Color(0xFFDAA1FC),
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Ingresa tu nombre", color = Color.White, fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.SemiBold, fontSize = 20.sp) },
                modifier = Modifier.fillMaxWidth()
                .shadow(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
                textStyle = TextStyle(color = Color.White, fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF4C2673),
                    unfocusedContainerColor = Color(0xFF4C2673),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = MoradoOscuro,

                )
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
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { selectedSymbol = "X" }
                        .alpha(if (selectedSymbol == "X") 1f else 0.5f)
                )
                // Botón de selección: Círculo
                Image(
                    painter = painterResource(id = R.drawable.circulo_image),
                    contentDescription = "Círculo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { selectedSymbol = "O" }
                        .alpha(if (selectedSymbol == "O") 1f else 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            FluorButton(
                text = "¡VAMOS A JUGAR!",
                onClick = { val name = if (playerName.isBlank()) "Extraño" else playerName
                    onStartGame(name, selectedSymbol)},
                modifier = Modifier.padding(horizontal=30.dp,vertical=15.dp)
                    .width(250.dp)
                    .height(64.dp)

            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onStartGame = { _, _ -> })
}

