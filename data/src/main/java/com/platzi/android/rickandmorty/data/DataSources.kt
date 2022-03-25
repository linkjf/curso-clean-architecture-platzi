package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Entities
import com.platzi.android.rickandmorty.domain.Entities.Character
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface RemoteCharacterDataSource {
    fun getAllCharacters(page: Int): Single<List<Character>>

}

interface RemoteEpisodeDataSource {
    fun getCharacterEpisodes(episodes: List<String>): Single<List<Entities.Episode>>

}

interface LocalCharacterDataSource {
    fun getAllFavoriteCharacters(): Flowable<List<Character>>

    fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean>

    fun updateFavoriteCharacterStatus(character: Character): Maybe<Boolean>
}
