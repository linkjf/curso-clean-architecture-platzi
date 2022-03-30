package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.data.RemoteCharacterDataSource
import com.platzi.android.rickandmorty.data.RemoteEpisodeDataSource
import com.platzi.android.rickandmorty.requestmanager.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

private const val BASE_URL_PROVIDER_KEY = "baseUrl"

@InstallIn(ViewModelComponent::class)
@Module
class RemoteDataSourceModule {

    @ViewModelScoped
    @Provides
    @Named(BASE_URL_PROVIDER_KEY)
    fun baseUrlProvider(): String = APIConstants.BASE_API_URL

    @ViewModelScoped
    @Provides
    fun characterRequestProvider(
        @Named(BASE_URL_PROVIDER_KEY) baseUrl: String
    ) = CharacterRequest(baseUrl)

    @ViewModelScoped
    @Provides
    fun remoteCharacterDataSourceProvider(
        characterRequest: CharacterRequest
    ): RemoteCharacterDataSource = CharacterRetrofitDataSource(characterRequest)

    @ViewModelScoped
    @Provides
    fun episodeRequestProvider(
        @Named("baseUrl") baseUrl: String
    ): EpisodeRequest = EpisodeRequest(baseUrl)

    @ViewModelScoped
    @Provides
    fun remoteEpisodeDataSourceProvider(
        episodeRequest: EpisodeRequest
    ): RemoteEpisodeDataSource = EpisodeRetrofitDataSource(episodeRequest)
}
