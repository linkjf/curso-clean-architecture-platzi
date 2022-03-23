package com.platzi.android.rickandmorty.database

import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.domain.Entities.Location
import com.platzi.android.rickandmorty.domain.Entities.Origin

fun List<CharacterEntity>.toCharacterDomainList() = map(CharacterEntity::toCharacterDomain)

fun CharacterEntity.toCharacterDomain() = Character(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginDomain(),
    location.toLocationDomain(),
    episodeList
)

fun Character.toCharacterEntity() = CharacterEntity(
    id,
    name,
    image,
    gender,
    species,
    status,
    origin.toOriginEntity(),
    location.toLocationEntity(),
    episodeList
)

fun OriginEntity.toOriginDomain() = Origin(
    originName,
    originUrl
)

fun Origin.toOriginEntity() = OriginEntity(
    name,
    url
)


fun LocationEntity.toLocationDomain() = Location(
    locationName,
    locationUrl
)

fun Location.toLocationEntity() = LocationEntity(
    name,
    url
)
