package com.example.tateti.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tateti.ui.theme.MoradoOscuro
import com.example.tateti.ui.theme.TicTacToeTheme
import android.media.MediaPlayer
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.example.tateti.R
import com.example.tateti.ui.theme.MoradoClaro
import com.example.tateti.ui.theme.MoradoIntermedio
import com.example.tateti.ui.theme.QuicksandFontFamily
import com.tuapp.ui.components.FluorButton
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale


@Composable
fun ResultScreen(
    resultMessage: String,
    onPlayAgain: () -> Unit,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val soundResId = when {
            resultMessage.contains("Wow", ignoreCase = true) -> R.raw.win_sound
            resultMessage.contains("La máquina gana!", ignoreCase = true) -> R.raw.lose_sound
            resultMessage.contains("Empate", ignoreCase = true) -> R.raw.draw_sound
            else -> null
        }

        soundResId?.let {
            val mediaPlayer = MediaPlayer.create(context, it)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp -> mp.release() }
        }
    }

    val imageResId = when {
        resultMessage.contains("Wow", ignoreCase = true) -> R.drawable.happy_face
        resultMessage.contains("La máquina gana", ignoreCase = true) -> R.drawable.sad_face
        resultMessage.contains("Empate", ignoreCase = true) -> R.drawable.neutral_face
        else -> null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(MoradoOscuro, MoradoIntermedio,MoradoClaro))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Resultado Emoji",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = resultMessage,
                fontSize = 25.sp,
                fontFamily = QuicksandFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFDAA1FC)
            )
            Spacer(modifier = Modifier.height(120.dp))
            FluorButton(
                text = "JUGAR DE NUEVO",
                onClick = onPlayAgain,
                modifier = Modifier.padding(horizontal=30.dp,vertical=15.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onGoHome,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Volver al inicio",
                    fontSize = 18.sp,
                    fontFamily = QuicksandFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun ResultScreenPreview() {
    TicTacToeTheme {
        ResultScreen(
            resultMessage = "¡Wow, Laura ganó!",
            onPlayAgain = {},
            onGoHome = {}
        )
    }
}
