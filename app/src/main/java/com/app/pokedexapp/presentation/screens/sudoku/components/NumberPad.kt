package com.app.pokedexapp.presentation.screens.sudoku.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberPad(
    maxNumber: Int,
    onNumberSelected: (Int) -> Unit,
) {
    val rows = if (maxNumber > 5) 2 else 1

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (i in 0 until rows) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val start = i * 5 + 1
                val end = minOf((i + 1) * 5, maxNumber)
                for (num in start..end) {
                    Button(
                        onClick = { onNumberSelected(num) },
                        modifier = Modifier.size(50.dp),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(text = num.toString())
                    }
                }
            }
        }
    }
}
