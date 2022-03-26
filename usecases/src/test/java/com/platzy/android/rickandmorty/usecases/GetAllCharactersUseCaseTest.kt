package com.platzy.android.rickandmorty.usecases

import com.platzi.android.rickandmorty.data.CharacterRepository
import com.platzi.android.rickandmorty.domain.Entities
import com.platzi.android.rickandmorty.usecases.GetAllCharacterUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class GetAllCharactersUseCaseTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var getAllCharacterUseCase: GetAllCharacterUseCase

    @Before
    fun setUp() {
        getAllCharacterUseCase = GetAllCharacterUseCase(characterRepository)
    }

    @Test
    fun `get all character use case should return a list of characters given a page number`() {

        val expectedResult = listOf(mockedCharacter.copy(id = 1))
        given(characterRepository.getAllCharacters(any())).willReturn(Single.just(expectedResult))

        getAllCharacterUseCase.invoke(1)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(expectedResult)
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

