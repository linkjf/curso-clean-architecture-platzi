package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.api.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetAllCharacterUseCase(
    private val characterRequest: CharacterRequest
) {
    fun invoke(currentPage: Int) = characterRequest
        .getService<CharacterService>()
        .getAllCharacters(currentPage)
        .map(CharacterResponseServer::toCharacterDomainList)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

}
