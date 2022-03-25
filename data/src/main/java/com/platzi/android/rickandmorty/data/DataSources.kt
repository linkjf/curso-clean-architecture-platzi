package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Entities.Character
import io.reactivex.Single

interface RemoteCharacterDataSource {
    fun getAllCharacters(page: Int): Single<List<Character>>
}
