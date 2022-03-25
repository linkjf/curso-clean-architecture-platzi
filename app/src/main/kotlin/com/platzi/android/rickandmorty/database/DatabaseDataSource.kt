package com.platzi.android.rickandmorty.database

import com.platzi.android.rickandmorty.data.LocalCharacterDataSource
import com.platzi.android.rickandmorty.domain.Entities
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterRoomDataSource(private val characterDatabase: CharacterDatabase) : LocalCharacterDataSource {

    private val characterDao by lazy { characterDatabase.characterDao() }

    override fun getAllFavoriteCharacters(): Flowable<List<Entities.Character>> = characterDao
        .getAllFavoriteCharacters()
        .map(List<CharacterEntity>::toCharacterDomainList)
        .onErrorReturn { emptyList() }
        .subscribeOn(Schedulers.io())


    override fun getFavoriteCharacterStatus(id: Int): Maybe<Boolean> = characterDao.getCharacterById(id)
        .isEmpty
        .flatMapMaybe { isEmpty ->
            Maybe.just(!isEmpty)
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())


    override fun updateFavoriteCharacterStatus(character: Entities.Character): Maybe<Boolean> {
        val characterEntity = character.toCharacterEntity()
        return characterDao.getCharacterById(characterEntity.id)
            .isEmpty
            .flatMapMaybe { isEmpty ->
                if (isEmpty) {
                    characterDao.insertCharacter(characterEntity)
                } else {
                    characterDao.deleteCharacter(characterEntity)
                }
                Maybe.just(isEmpty)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

}
