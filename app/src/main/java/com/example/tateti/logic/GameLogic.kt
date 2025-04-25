package com.example.tateti.logic

fun getWinningCombo(board: List<String>, symbol: String): List<Int>? {
    val combos = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
        listOf(0, 4, 8), listOf(2, 4, 6)
    )
    return combos.firstOrNull { combo -> combo.all { board[it] == symbol } }
}

fun isDraw(board: List<String>): Boolean = board.all { it.isNotBlank() }

fun minimax(
    board: List<String>,
    ai: String,
    player: String,
    depth: Int = 0,
    isMax: Boolean,
    alpha: Int,
    beta: Int
): Int {
    if (getWinningCombo(board, ai) != null) return 10 - depth
    if (getWinningCombo(board, player) != null) return depth - 10
    if (isDraw(board)) return 0

    var bestScore = if (isMax) Int.MIN_VALUE else Int.MAX_VALUE
    var a = alpha
    var b = beta

    for (i in board.indices) {
        if (board[i] == "") {
            val newBoard = board.toMutableList()
            newBoard[i] = if (isMax) ai else player
            val score = minimax(newBoard, ai, player, depth + 1, !isMax, a, b)
            if (isMax) {
                bestScore = maxOf(bestScore, score)
                a = maxOf(a, bestScore)
            } else {
                bestScore = minOf(bestScore, score)
                b = minOf(b, bestScore)
            }
            if (b <= a) break
        }
    }
    return bestScore
}
