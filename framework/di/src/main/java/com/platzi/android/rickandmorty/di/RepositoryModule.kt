package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun characterRepositoryProvider(
        remoteCharacterDataSource: RemoteCharacterDataSource,
        localCharacterDataSource: LocalCharacterDataSource
    ): CharacterRepository {
        return CharacterRepository(remoteCharacterDataSource, localCharacterDataSource)
    }

    @ViewModelScoped
    @Provides
    fun episodeRepositoryProvider(
        episodeDataSource: RemoteEpisodeDataSource
    ): EpisodeRepository {
        return EpisodeRepository(episodeDataSource)
    }
}
