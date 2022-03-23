package com.platzi.android.rickandmorty.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.android.rickandmorty.api.CharacterServer
import com.platzi.android.rickandmorty.api.EpisodeServer
import com.platzi.android.rickandmorty.api.toCharacterEntity
import com.platzi.android.rickandmorty.database.CharacterDao
import com.platzi.android.rickandmorty.database.CharacterEntity
import com.platzi.android.rickandmorty.usecases.GetEpisodeFromCharacterUseCase
import com.platzi.android.rickandmorty.usecases.GetFavoriteCharacterUseCase
import com.platzi.android.rickandmorty.usecases.SetFavoriteUseCase
import io.reactivex.disposables.CompositeDisposable

class CharacterDetailViewModel(
    private var character: CharacterServer? = null,
    private val getEpisodeFromCharacterUseCase: GetEpisodeFromCharacterUseCase,
    private val getFavoriteCharacterUseCase: GetFavoriteCharacterUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _characterData = MutableLiveData<CharacterServer>()
    val characterData get() = _characterData

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite get() = _isFavorite

    private val _events = MutableLiveData<Event<CharacterDetailNavigation>>()
    val events get() = _events

    fun onCharacterValidation() {
        if (character == null) {
            _events.value = Event(CharacterDetailNavigation.ShowCharacterDetailsError(Throwable()))
            return
        }
        _characterData.value = character
        onValidateFavoriteCharacterStatus(character!!.id)
        onShowEpisodeList(character!!.episodeList)
    }

    private fun onValidateFavoriteCharacterStatus(characterId: Int) {
        disposable.add(
            getFavoriteCharacterUseCase.invoke(characterId)
                .subscribe { isFavorite ->
                    _isFavorite.value = isFavorite
                }
        )
    }

    private fun onShowEpisodeList(episodeUrlList: List<String>) {
        disposable.add(
            getEpisodeFromCharacterUseCase.invoke(episodeUrlList)
                .doOnSubscribe {
                    _events.value = Event(CharacterDetailNavigation.ShowLoading)
                }
                .subscribe(
                    { episodeList ->
                        _events.value = Event(CharacterDetailNavigation.HideLoading)
                        _events.value = Event(CharacterDetailNavigation.ShowCharacterDetailsEpisodes(episodeList))
                    },
                    { error ->
                        _events.value = Event(CharacterDetailNavigation.HideLoading)
                        _events.value = Event(CharacterDetailNavigation.ShowCharacterDetailsError(error))
                    })
        )
    }

    fun onUpdateFavoriteCharacterStatus() {
        val characterEntity: CharacterEntity = character!!.toCharacterEntity()
        disposable.add(
            setFavoriteUseCase.invoke(characterEntity)
                .subscribe { isFavorite ->
                    _isFavorite.value = isFavorite
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class CharacterDetailNavigation {
        data class ShowCharacterDetailsEpisodes(val episodeList: List<EpisodeServer>) : CharacterDetailNavigation()
        data class ShowCharacterDetailsError(val error: Throwable) : CharacterDetailNavigation()
        object ShowLoading : CharacterDetailNavigation()
        object HideLoading : CharacterDetailNavigation()
    }
}
