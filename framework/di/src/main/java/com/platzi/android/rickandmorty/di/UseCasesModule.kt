package com.platzi.android.rickandmorty.di

import com.platzi.android.rickandmorty.data.CharacterRepository
import com.platzi.android.rickandmorty.data.EpisodeRepository
import com.platzi.android.rickandmorty.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun getAllCharacterUseCaseProvider(characterRepository: CharacterRepository): GetAllCharacterUseCase {
        return GetAllCharacterUseCase(characterRepository)
    }

    @ViewModelScoped
    @Provides
    fun getAllFavoriteCharactersUseCaseProvider(characterRepository: CharacterRepository): GetAllFavoriteCharactersUseCase {
        return GetAllFavoriteCharactersUseCase(characterRepository)
    }

    @ViewModelScoped
    @Provides
    fun getEpisodeFromCharacterUseCaseProvider(episodeRepository: EpisodeRepository): GetEpisodeFromCharacterUseCase {
        return GetEpisodeFromCharacterUseCase(episodeRepository)
    }

    @ViewModelScoped
    @Provides
    fun getFavoriteCharacterUseCaseProvider(characterRepository: CharacterRepository): GetFavoriteCharacterUseCase {
        return GetFavoriteCharacterUseCase(characterRepository)
    }

    @ViewModelScoped
    @Provides
    fun setFavoriteUseCaseProvider(characterRepository: CharacterRepository): SetFavoriteUseCase {
        return SetFavoriteUseCase(characterRepository)
    }
}
