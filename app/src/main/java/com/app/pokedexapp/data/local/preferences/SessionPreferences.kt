package com.app.pokedexapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.app.pokedexapp.domain.model.SudokuGame
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionPreferences
    @Inject
    constructor(
        @ApplicationContext context: Context,
        private val gson: Gson,
    ) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences(
                PreferencesConstants.PREF_NAME,
                Context.MODE_PRIVATE,
            )

        fun saveGame(game: SudokuGame) {
            prefs
                .edit()
                .putString(PreferencesConstants.KEY_SAVED_GAME, gson.toJson(game))
                .putLong(PreferencesConstants.KEY_LAST_PLAYED, System.currentTimeMillis())
                .apply()
        }

        fun getSavedGame(): SudokuGame? {
            val json = prefs.getString(PreferencesConstants.KEY_SAVED_GAME, null)

            return try {
                val type = object : TypeToken<SudokuGame>() {}.type
                gson.fromJson(json, type)
            } catch (e: Exception) {
                null
            }
        }

        fun hasSavedGame(): Boolean = prefs.contains(PreferencesConstants.KEY_SAVED_GAME)

        fun clearCache() {
            prefs
                .edit()
                .clear()
                .apply()
        }
    }
