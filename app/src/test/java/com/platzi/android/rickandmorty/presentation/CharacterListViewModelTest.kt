package com.platzi.android.rickandmorty.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.platzi.android.rickandmorty.domain.Entities
import com.platzi.android.rickandmorty.usecases.GetAllCharacterUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {

    @get:Rule
    val rxSchedulerRules = RxSchedulerRules()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getAllCharactersUseCase: GetAllCharacterUseCase

    @Mock
    lateinit var eventObserver: Observer<Event<CharacterListViewModel.CharacterListNavigation>>

    private lateinit var characterListViewModel: CharacterListViewModel

    @Before
    fun setUp() {
        characterListViewModel = CharacterListViewModel(getAllCharactersUseCase)
    }

    @Test
    fun `onGetAllCharacters should return an expected success list of characters`() {
        val expectedResult = listOf(mockedCharacter.copy(id = 1))
        given(getAllCharactersUseCase.invoke(any())).willReturn(Single.just(expectedResult))

        characterListViewModel.events.observeForever(eventObserver)
        characterListViewModel.onGetAllCharacters()

        verify(eventObserver).onChanged(Event(CharacterListViewModel.CharacterListNavigation.ShowCharacterList(expectedResult)))
        characterListViewModel.events.removeObserver(eventObserver)
    }

}

val mockedOrigin = Entities.Origin(
    "",
    ""
)

val mockedLocation = Entities.Location(
    "",
    ""
)

val mockedCharacter = Entities.Character(
    0,
    "",
    null,
    "",
    "",
    "",
    mockedOrigin,
    mockedLocation,
    emptyList()
)
