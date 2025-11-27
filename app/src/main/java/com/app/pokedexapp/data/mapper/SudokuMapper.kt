package com.app.pokedexapp.data.mapper

import com.app.pokedexapp.data.remote.dto.SudokuDto
import com.app.pokedexapp.domain.model.SudokuCell
import com.app.pokedexapp.domain.model.SudokuGame

fun SudokuDto.toDomain(
    difficulty: String,
    size: Int,
): SudokuGame {
    val cells = mutableListOf<List<SudokuCell>>()

    for (row in 0 until size) {
        val currentCells = mutableListOf<SudokuCell>()

        for (column in 0 until size) {
            val puzzleVal = this.puzzle[row][column]
            val solutionVal = this.solution[row][column]
            val isFromApi = (puzzleVal != null)

            val cell =
                SudokuCell(
                    row = row,
                    column = column,
                    currentVal = puzzleVal,
                    correctVal = solutionVal,
                    isFromApi = isFromApi,
                    isError = false,
                )

            currentCells.add(cell)
        }
        cells.add(currentCells)
    }

    return SudokuGame(
        board = cells,
        difficulty = difficulty,
        size = size,
        isSolved = false,
    )
}
