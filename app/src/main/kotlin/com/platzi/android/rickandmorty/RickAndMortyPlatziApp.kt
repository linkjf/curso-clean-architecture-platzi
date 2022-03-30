package com.platzi.android.rickandmorty

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickAndMortyPlatziApp : Application() {

    //region Override Methods & Callbacks

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    //endregion
}
