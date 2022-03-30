package com.platzi.android.rickandmorty.di

import android.app.Application
import com.platzi.android.rickandmorty.databasemanager.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun databaseProvider(application: Application): CharacterDatabase {
        return CharacterDatabase.getDatabase(application)
    }
}
