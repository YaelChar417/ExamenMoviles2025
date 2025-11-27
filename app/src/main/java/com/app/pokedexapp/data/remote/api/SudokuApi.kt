package com.app.pokedexapp.data.remote.api

import android.icu.number.IntegerWidth
import com.app.pokedexapp.data.remote.dto.SudokuDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SudokuApi {
    @Headers("X-Api-Key: wLVPN1zV08lJYF7uXqgyPw==zVwp6TlVcAO1NLUf")
    @GET("v1/sudokugenerate")
    suspend fun getSudoku(
        @Query("width") width: Int,
        @Query("height") height: Int,
        @Query("difficulty") difficulty: String,
    ): SudokuDto
}
