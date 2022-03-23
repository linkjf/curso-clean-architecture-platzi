package com.platzi.android.rickandmorty.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.usecases.GetAllFavoriteCharactersUseCase
import io.reactivex.disposables.CompositeDisposable

class FavoriteListViewModel(
        private val getAllFavoriteCharactersUseCase: GetAllFavoriteCharactersUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _events = MutableLiveData<Event<FavoriteListNavigation>>()
    val events get() = _events

    private val _favoriteCharacterList: LiveData<List<Character>>
        get() = LiveDataReactiveStreams.fromPublisher(getAllFavoriteCharactersUseCase.invoke())

    val favoriteCharacterList: LiveData<List<Character>>
        get() = _favoriteCharacterList

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun onFavoriteCharacterList(list: List<Character>) {
        if (list.isEmpty()) {
            _events.value = Event(FavoriteListNavigation.ShowEmptyList)
            return
        }
        _events.value = Event(FavoriteListNavigation.ShowCharacterList(list))
    }

    sealed class FavoriteListNavigation {
        data class ShowCharacterList(val characterList: List<Character>) : FavoriteListNavigation()
        object ShowEmptyList : FavoriteListNavigation()

    }
}
