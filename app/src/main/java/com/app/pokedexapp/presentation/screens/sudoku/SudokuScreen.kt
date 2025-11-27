package com.app.pokedexapp.presentation.screens.sudoku

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.pokedexapp.presentation.screens.sudoku.components.GameContentScreen
import com.app.pokedexapp.presentation.screens.sudoku.components.SelectionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sudoku Ninja") },
                actions = {
                    // Si hay un juego activo, mostramos botón menú
                    if (uiState.game != null) {
                        IconButton(onClick = { viewModel.returnToMenu() }) {
                            Text("Menu", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = uiState.error ?: "Ocurrió un error, vuelva a intentarlo",
                        color = androidx.compose.ui.graphics.Color.Red
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.returnToMenu() }) {
                        Text(text = "Regresar")
                    }
                }
            } else if (uiState.game == null) {
                SelectionScreen(
                    hasSavedGame = viewModel.hasSavedGameAvailable(),
                    onNewGameClick = { size, difficulty -> viewModel.loadNewGame(size, difficulty)},
                    onLoadGameClick = { viewModel.loadSavedGame() }
                )
            } else {
                GameContentScreen(
                    state = uiState,
                    selectedCell = selectedCell,
                    onCellClick = { row, column -> selectedCell = row to column },
                    onInput = { num ->
                        selectedCell?.let { (row, column) ->
                            viewModel.onCellInput(row, column, num)
                            selectedCell = null
                        }
                    },
                    onVerifyClick = { viewModel.verifySolution() },
                    onResetClick = { viewModel.resetGame() },
                    onNewPuzzleClick = {
                        val game = uiState.game
                        if (game != null) {
                            viewModel.loadNewGame(game.size, game.difficulty)
                        }
                    }
                )
            }
        }
    }
}
