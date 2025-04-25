package com.example.tateti.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameBoard(
    board: List<String>,
    onCellClick: (Int) -> Unit,
    winningCombo: List<Int>?,
    resultText: String
) {
    val spacing: Dp = 12.dp

    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .aspectRatio(1f)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1A004D), Color(0xFF3B0D7D))
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .border(3.dp, Color(0xFFB388FF).copy(alpha = 0.8f), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val totalSpacing = spacing * 2
            val cellSize = (maxWidth - totalSpacing) / 3
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing),
                modifier = Modifier.fillMaxSize()
            ) {
                for (row in 0 until 3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(spacing),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            val value = board[index]
                            val scale by animateFloatAsState(
                                targetValue = if (value.isNotEmpty()) 1f else 0.9f,
                                animationSpec = tween(300)
                            )

                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF130036))
                                    .border(
                                        2.dp,
                                        Color.White.copy(alpha = 0.1f),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable(enabled = value.isEmpty() && resultText.isEmpty()) {
                                        onCellClick(index)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = value,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale),
                                    color = when (value) {
                                        "X" -> Color(0xFFFF4081)
                                        "O" -> Color(0xFF64FFDA)
                                        else -> Color.Transparent
                                    },
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = when (value) {
                                                "X" -> Color(0xFFFF4081)
                                                "O" -> Color(0xFF64FFDA)
                                                else -> Color.Transparent
                                            },
                                            offset = Offset(2f, 2f),
                                            blurRadius = 4f
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }

            winningCombo?.let { combo ->
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val spacingPx = spacing.toPx()
                    val cellPx = ((maxWidth - totalSpacing) / 3).toPx()

                    val startIndex = combo.first()
                    val endIndex = combo.last()

                    val startX = (startIndex % 3) * (cellPx + spacingPx) + cellPx / 2
                    val startY = (startIndex / 3) * (cellPx + spacingPx) + cellPx / 2
                    val endX = (endIndex % 3) * (cellPx + spacingPx) + cellPx / 2
                    val endY = (endIndex / 3) * (cellPx + spacingPx) + cellPx / 2

                    drawLine(
                        color = Color.Yellow.copy(alpha = 0.85f),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 16f
                    )
                }
            }
        }
    }
}