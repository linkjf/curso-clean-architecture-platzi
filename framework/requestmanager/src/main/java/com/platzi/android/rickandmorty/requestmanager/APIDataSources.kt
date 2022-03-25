package com.platzi.android.rickandmorty.requestmanager

import com.platzi.android.rickandmorty.data.RemoteCharacterDataSource
import com.platzi.android.rickandmorty.data.RemoteEpisodeDataSource
import com.platzi.android.rickandmorty.domain.Entities
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterRetrofitDataSource(private val characterRequest: CharacterRequest) : RemoteCharacterDataSource {
    override fun getAllCharacters(page: Int): Single<List<Entities.Character>> {
        return characterRequest
            .getService<CharacterService>()
            .getAllCharacters(page)
            .map(CharacterResponseServer::toCharacterDomainList)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}

class EpisodeRetrofitDataSource(private val episodeRequest: EpisodeRequest) : RemoteEpisodeDataSource {
    override fun getCharacterEpisodes(episodes: List<String>): Single<List<Entities.Episode>> {
        return Observable.fromIterable(episodes)
            .flatMap { episode: String ->
                episodeRequest.baseUrl = episode
                episodeRequest
                    .getService<EpisodeService>()
                    .getEpisode()
                    .map(EpisodeServer::toEpisodeDomain)
                    .toObservable()
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}
