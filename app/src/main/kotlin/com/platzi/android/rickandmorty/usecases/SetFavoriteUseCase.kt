package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.CharacterRepository
import com.platzi.android.rickandmorty.domain.Entities.Character
import io.reactivex.Maybe

class SetFavoriteUseCase(private val characterRepository: CharacterRepository) {
    fun invoke(character: Character): Maybe<Boolean> = characterRepository.updateFavoriteCharacterStatus(character)
}
