package com.app.pokedexapp.presentation.screens.sudoku.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectionScreen(
    hasSavedGame: Boolean,
    onNewGameClick: (Int, String) -> Unit,
    onLoadGameClick: () -> Unit
) {
    var selectedSize by remember { mutableStateOf(4) }
    var selectedDifficulty by remember { mutableStateOf("easy") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Nueva Partida",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Tamaño: $selectedSize x $selectedSize")
        Row {
            Button(onClick = {selectedSize = 4}) {
                Text(text = "4 x 4")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {selectedSize = 9}) {
                Text(text = "9 x 9")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Dificultad: $selectedDifficulty")
        Row {
            Button(onClick = {selectedDifficulty = "easy"}) {
                Text(text = "Fácil")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = {selectedDifficulty = "medium"}) {
                Text(text = "Normal")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = {selectedDifficulty = "hard"}) {
                Text(text = "Díficil")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNewGameClick(selectedSize, selectedDifficulty) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Nuevo Juego")
        }

        if (hasSavedGame) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onLoadGameClick,
            ) {
                Text(text = "Continuar partida guardada")
            }
        }
    }
}