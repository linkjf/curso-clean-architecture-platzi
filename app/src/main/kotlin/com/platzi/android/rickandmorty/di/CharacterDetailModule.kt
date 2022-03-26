package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel
import com.platzi.android.rickandmorty.usecases.GetEpisodeFromCharacterUseCase
import com.platzi.android.rickandmorty.usecases.GetFavoriteCharacterUseCase
import com.platzi.android.rickandmorty.usecases.SetFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CharacterDetailModule(
    private val character: Character
) {

    @Provides
    fun characterDetailViewModelProvider(
        getEpisodeFromCharacterUseCase: GetEpisodeFromCharacterUseCase,
        getFavoriteCharacterUseCase: GetFavoriteCharacterUseCase,
        setFavoriteUseCase: SetFavoriteUseCase
    ): CharacterDetailViewModel =
        CharacterDetailViewModel(character, getEpisodeFromCharacterUseCase, getFavoriteCharacterUseCase, setFavoriteUseCase)

    @Subcomponent(modules = [CharacterDetailModule::class])
    interface CharacterDetailComponent {
        val characterDetailViewModel: CharacterDetailViewModel
    }
}
