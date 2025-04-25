package com.example.tateti.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.example.tateti.ui.theme.QuicksandFontFamily
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TimerProgressBar(timeLeft: Int, maxTime: Int) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = timeLeft / maxTime.toFloat(),
            color = if (timeLeft <= 3) Color.Red else Color(0xFFEFA100),
            strokeWidth = 6.dp,
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = "0:${timeLeft.toString().padStart(2, '0')}",
            fontSize = 20.sp,
            fontFamily = QuicksandFontFamily,
            fontWeight = FontWeight.Bold,
            color = if (timeLeft <= 3) Color.Red else Color.White
        )
    }
}
