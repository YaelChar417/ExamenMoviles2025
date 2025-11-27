package com.app.pokedexapp.di

import android.content.Context
import android.se.omapi.Session
import com.app.pokedexapp.data.local.preferences.SessionPreferences
import com.app.pokedexapp.data.remote.api.SudokuApi
import com.app.pokedexapp.data.repository.SudokuRepositoryImpl
import com.app.pokedexapp.domain.repository.SudokuRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSudokuApi(retrofit: Retrofit): SudokuApi = retrofit.create(SudokuApi::class.java)

    @Provides
    @Singleton
    fun provideSudokuPreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): SessionPreferences = SessionPreferences(context, gson)

    @Provides
    @Singleton
    fun provideSudokuRepository(
        api: SudokuApi,
        preferences: SessionPreferences,
    ): SudokuRepository = SudokuRepositoryImpl(api, preferences)
}
