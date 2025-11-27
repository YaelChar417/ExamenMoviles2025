package com.app.pokedexapp.presentation.screens.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pokedexapp.domain.common.Result
import com.app.pokedexapp.domain.model.SudokuCell
import com.app.pokedexapp.domain.usecase.CheckSaveGameUseCase
import com.app.pokedexapp.domain.usecase.GetNewGameUseCase
import com.app.pokedexapp.domain.usecase.LoadGameUseCase
import com.app.pokedexapp.domain.usecase.SaveGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel
    @Inject
    constructor(
        private val getNewGameUseCase: GetNewGameUseCase,
        private val loadGameUseCase: LoadGameUseCase,
        private val saveGameUseCase: SaveGameUseCase,
        private val checkSaveGameUseCase: CheckSaveGameUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SudokuUiState())
        val uiState: StateFlow<SudokuUiState> = _uiState.asStateFlow()

        private var hasSavedGame = false

        init {
            checkSavedGame()
        }

        private fun checkSavedGame() {
            viewModelScope.launch {
                checkSaveGameUseCase().collect { result ->
                    if (result is Result.Success) {
                        hasSavedGame = result.data
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }
        }

        fun loadNewGame(
            size: Int,
            difficulty: String,
        ) {
            viewModelScope.launch {
                getNewGameUseCase(
                    size = size,
                    difficulty = difficulty,
                ).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = true,
                                    error = null,
                                )
                        }

                        is Result.Success -> {
                            _uiState.value =
                                SudokuUiState(
                                    game = result.data,
                                    isLoading = false,
                                    error = null,
                                    message = null,
                                    isGameDone = false,
                                )
                            saveGame()
                        }

                        is Result.Error -> {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    error = result.exception.message ?: "Error desconocido",
                                )
                        }
                    }
                }
            }
        }

        fun loadSavedGame() {
            viewModelScope.launch {
                loadGameUseCase().collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = true,
                                    error = null,
                                )
                        }

                        is Result.Success -> {
                            _uiState.value =
                                SudokuUiState(
                                    game = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                        }

                        is Result.Error -> {
                            _uiState.value =
                                _uiState.value.copy(
                                    isLoading = false,
                                    error = result.exception.message ?: "No se pudo cargar la partida guardada",
                                )
                        }
                    }
                }
            }
        }

        fun onCellInput(
            row: Int,
            column: Int,
            number: Int,
        ) {
            val currentGame = _uiState.value.game ?: return

            val newBoard = mutableListOf<List<SudokuCell>>()
            for (r in currentGame.board) {
                val newRow =
                    r.map { cell ->
                        if (cell.row == row &&
                            cell.column == column &&
                            !cell.isFromApi
                        ) {
                            cell.copy(
                                currentVal = number,
                                isError = false,
                            )
                        } else {
                            cell
                        }
                    }
                newBoard.add(newRow)
            }

            val newGame = currentGame.copy(board = newBoard)
            _uiState.value =
                _uiState.value.copy(
                    game = newGame,
                    message = null,
                )
            saveGame()
        }

        fun resetGame() {
            val currentGame = _uiState.value.game ?: return
            val newBoard =
                currentGame.board.map { r ->
                    r.map { cell ->
                        if (!cell.isFromApi) {
                            cell.copy(
                                currentVal = null,
                                isError = false,
                            )
                        } else {
                            cell
                        }
                    }
                }
            val newGame = currentGame.copy(board = newBoard)
            _uiState.value =
                _uiState.value.copy(
                    game = newGame,
                    message = "El juego ha sido reiniciado",
                    isGameDone = false,
                )
            saveGame()
        }

        fun verifySolution() {
            val currentGame = _uiState.value.game ?: return
            var isCorrect = true
            var isFilled = true

            val newBoard =
                currentGame.board.map { row ->
                    row.map { cell ->
                        if (cell.currentVal == null) {
                            isFilled = false
                            cell
                        } else if (cell.currentVal != cell.correctVal) {
                            isCorrect = false
                            cell.copy(isError = true)
                        } else {
                            cell.copy(isError = false)
                        }
                    }
                }

            if (!isFilled) {
                _uiState.value =
                    _uiState.value.copy(
                        game = currentGame.copy(board = newBoard),
                        message = "Por favor, complete todas las celdas",
                    )
                return
            }

            if (isCorrect) {
                _uiState.value =
                    _uiState.value.copy(
                        game = currentGame.copy(board = newBoard),
                        message = "Felicidades!!! has encontrado la solución",
                        isGameDone = true,
                    )
            } else {
                _uiState.value =
                    _uiState.value.copy(
                        game = currentGame.copy(board = newBoard),
                        message = "Verifica bien tu solución",
                    )
            }
        }

        private fun saveGame() {
            val currentGame = _uiState.value.game ?: return
            viewModelScope.launch {
                saveGameUseCase(currentGame).collect {
                    if (it is Result.Success) {
                        hasSavedGame = true
                    }
                }
            }
        }

        fun hasSavedGameAvailable() = hasSavedGame

        fun returnToMenu() {
            _uiState.value = SudokuUiState()
            checkSavedGame()
        }
    }
