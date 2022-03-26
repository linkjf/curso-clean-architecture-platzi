package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Entities

class CharacterRepository(
    private val remoteCharacterDataSource: RemoteCharacterDataSource,
    private val localCharacterDataSource: LocalCharacterDataSource
) {
    fun getAllCharacters(page: Int) = remoteCharacterDataSource.getAllCharacters(page)

    fun getAllFavoriteCharacters() = localCharacterDataSource.getAllFavoriteCharacters()

    fun getFavoriteCharacterStatus(id: Int) = localCharacterDataSource.getFavoriteCharacterStatus(id)

    fun updateFavoriteCharacterStatus(character: Entities.Character) = localCharacterDataSource.updateFavoriteCharacterStatus(character)
}

class EpisodeRepository(private val remoteEpisodeDataSource: RemoteEpisodeDataSource) {
    fun getCharacterEpisodes(episodes: List<String>) = remoteEpisodeDataSource.getCharacterEpisodes(episodes)
}
