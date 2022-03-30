package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.CharacterRepository
import com.platzi.android.rickandmorty.data.EpisodeRepository
import com.platzi.android.rickandmorty.domain.Entities
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class GetAllCharacterUseCase(private val characterRepository: CharacterRepository) {
    fun invoke(currentPage: Int) = characterRepository.getAllCharacters(currentPage)
}

class GetAllFavoriteCharactersUseCase(private val characterRepository: CharacterRepository) {
    fun invoke(): Flowable<List<Entities.Character>> = characterRepository.getAllFavoriteCharacters()
}

class GetEpisodeFromCharacterUseCase(private val episodeRepository: EpisodeRepository) {
    fun invoke(episodeList: List<String>): Single<List<Entities.Episode>> = episodeRepository.getCharacterEpisodes(episodeList)
}

class GetFavoriteCharacterUseCase(private val characterRepository: CharacterRepository) {
    fun invoke(characterId: Int) = characterRepository.getFavoriteCharacterStatus(characterId)
}

class SetFavoriteUseCase(private val characterRepository: CharacterRepository) {
    fun invoke(character: Entities.Character): Maybe<Boolean> = characterRepository.updateFavoriteCharacterStatus(character)
}
