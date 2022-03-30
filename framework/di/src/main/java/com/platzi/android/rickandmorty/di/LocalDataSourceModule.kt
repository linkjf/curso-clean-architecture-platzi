package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.data.LocalCharacterDataSource
import com.platzi.android.rickandmorty.databasemanager.CharacterDatabase
import com.platzi.android.rickandmorty.databasemanager.CharacterRoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class LocalDataSourceModule {

    @ViewModelScoped
    @Provides
    fun localCharacterDataSourceProvider(
        database: CharacterDatabase,
    ): LocalCharacterDataSource {
        return CharacterRoomDataSource(database)
    }
}
