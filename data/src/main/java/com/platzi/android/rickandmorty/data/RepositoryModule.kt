package com.platzi.android.rickandmorty.data

import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun characterRepositoryProvider(
        remoteCharacterDataSource: RemoteCharacterDataSource,
        localCharacterDataSource: LocalCharacterDataSource
    ): CharacterRepository = CharacterRepository(remoteCharacterDataSource, localCharacterDataSource)

    @Provides
    fun episodeRepositoryProvider(
        episodeDataSource: RemoteEpisodeDataSource
    ): EpisodeRepository = EpisodeRepository(episodeDataSource)
}
