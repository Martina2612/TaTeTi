package com.tuapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tateti.ui.theme.MoradoOscuro
import com.example.tateti.ui.theme.QuicksandFontFamily

@Composable
fun FluorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MoradoOscuro),
        modifier = modifier
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFF00FF), Color(0xFF00FFFF))
                ),
                shape = RoundedCornerShape(25.dp)
            )
            .background(
                color = MoradoOscuro,
                shape = RoundedCornerShape(25.dp)
            )
            .shadow(12.dp, RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Magenta,
            fontWeight = FontWeight.Bold,
            fontFamily = QuicksandFontFamily,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Magenta,
                    offset = Offset(0f, 0f),
                    blurRadius = 25f
                )
            )
        )
    }
}


