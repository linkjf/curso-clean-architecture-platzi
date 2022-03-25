package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.CharacterRepository

class GetFavoriteCharacterUseCase(private val characterRepository: CharacterRepository) {
    fun invoke(characterId: Int) = characterRepository.getFavoriteCharacterStatus(characterId)
}
