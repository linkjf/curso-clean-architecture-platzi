package com.platzi.android.rickandmorty.presentation

data class Event<out T>(private val content: T) {

    private var hasBennHandled = false

    fun getContentIfNotHandled(): T? =
        if (hasBennHandled) {
            null
        } else {
            hasBennHandled = true
            content
        }
}

