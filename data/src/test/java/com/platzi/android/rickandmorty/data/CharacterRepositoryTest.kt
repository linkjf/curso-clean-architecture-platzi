package com.platzi.android.rickandmorty.data

import com.platzi.android.rickandmorty.domain.Entities
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryTest {

    @Mock
    private lateinit var localCharacterDataSource: LocalCharacterDataSource

    @Mock
    private lateinit var remoteCharacterDataSource: RemoteCharacterDataSource

    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setUp() {
        characterRepository = CharacterRepository(remoteCharacterDataSource, localCharacterDataSource)
    }

    @Test
    fun `getAllCharacters should return an expected list of characters given a page number`() {

        val expectedResult = listOf(mockedCharacter.copy(id = 1))

        given(remoteCharacterDataSource.getAllCharacters(any())).willReturn(Single.just(expectedResult))

        characterRepository.getAllCharacters(1)
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
