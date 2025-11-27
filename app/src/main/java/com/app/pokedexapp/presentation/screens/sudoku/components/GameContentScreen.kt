package com.app.pokedexapp.presentation.screens.sudoku.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.pokedexapp.presentation.screens.sudoku.SudokuUiState

@Composable
fun GameContentScreen(
    state: SudokuUiState,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit,
    onInput: (Int) -> Unit,
    onVerifyClick: () -> Unit,
    onResetClick: () -> Unit,
    onNewPuzzleClick: () -> Unit,
) {
    val game = state.game ?: return
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var textFieldValue by remember { mutableStateOf("") }

    LaunchedEffect(selectedCell) {
        if (selectedCell != null) {
            focusRequester.requestFocus()
            keyboardController?.show()
            textFieldValue = ""
        } else {
            keyboardController?.hide()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (state.message != null) {
            Text(
                text = state.message,
                color = if (state.isGameDone) Color(0xFF2E7D32) else Color.Red,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
            )
        }

        SudokuBoardScreen(
            game = game,
            selectedCell = selectedCell,
            onCellClick = onCellClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = textFieldValue,
            onValueChange = { newText ->
                val digits = newText.filter { it.isDigit() }

                if (digits.length < 3) {
                    textFieldValue = digits

                    val number = digits.toIntOrNull()
                    if (number != null && number > 0) {
                        onInput(number)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .alpha(0f)
                    .size(1.dp)
                    .focusRequester(focusRequester),
        )

        if (selectedCell != null && !state.isGameDone) {
            Text(
                text = "Escribe el número con tu teclado",
                style = MaterialTheme.typography.labelLarge,
            )
        } else if (!state.isGameDone) {
            Text(text = "Toca una celda para editar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Button(
                onClick = onVerifyClick,
                enabled = !state.isGameDone,
            ) {
                Text(text = "Verificar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = onResetClick) {
                Text(text = "Reiniciar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNewPuzzleClick) {
            Text(text = "Solicitar Nuevo Juego")
        }
    }
}
