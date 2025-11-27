package com.app.pokedexapp.presentation.screens.sudoku.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.pokedexapp.domain.model.SudokuGame
import kotlin.math.sqrt

@Composable
fun SudokuBoardScreen(
    game: SudokuGame,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    val size = game.size
    val blockSize = sqrt(size.toDouble()).toInt()

    Column(
        modifier = Modifier
            .border(2.dp, Color.Black)
            .background(Color.Black)
    ) {
        for (row in 0 until size) {
            Row {
                for (col in 0 until size) {
                    val cell = game.board[row][col]

                    val isRightBlockBorder = (col + 1) % blockSize == 0 && col != size - 1
                    val isBottomBlockBorder = (row + 1) % blockSize == 0 && row != size - 1

                    // Determinar color de fondo
                    val backgroundColor = when {
                        cell.isError -> Color(0xFFFFCDD2)
                        selectedCell == (row to col) -> Color(0xFFBBDEFB)
                        cell.isFromApi -> Color(0xFFE0E0E0)
                        else -> Color.White
                    }

                    Box(
                        modifier = Modifier
                            .size(if (size == 9) 36.dp else 60.dp)
                            .background(backgroundColor)
                            .padding(
                                end = if (isRightBlockBorder) 2.dp else 1.dp,
                                bottom = if (isBottomBlockBorder) 2.dp else 1.dp
                            )
                            .clickable(enabled = !cell.isFromApi) {
                                onCellClick(row, col)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cell.currentVal?.toString() ?: "",
                            fontSize = 18.sp,
                            fontWeight = if (cell.isFromApi) FontWeight.Bold else FontWeight.Normal,
                            color = if (cell.isFromApi) Color.Black else Color(0xFF1565C0)
                        )
                    }
                }
            }
        }
    }
}
