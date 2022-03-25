package com.platzi.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.EpisodeRepository
import com.platzi.android.rickandmorty.domain.Entities.Episode
import io.reactivex.Single

class GetEpisodeFromCharacterUseCase(private val episodeRepository: EpisodeRepository) {

    fun invoke(episodeList: List<String>): Single<List<Episode>> = episodeRepository.getCharacterEpisodes(episodeList)
}
