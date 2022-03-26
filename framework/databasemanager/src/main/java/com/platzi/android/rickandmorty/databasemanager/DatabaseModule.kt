package com.platzi.android.rickandmorty.databasemanager

import android.app.Application
import com.platzi.android.rickandmorty.data.LocalCharacterDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun databaseProvider(application: Application): CharacterDatabase = CharacterDatabase.getDatabase(application)

    @Provides
    fun localCharacterDataSourceProvider(database: CharacterDatabase): LocalCharacterDataSource = CharacterRoomDataSource(database)
}
