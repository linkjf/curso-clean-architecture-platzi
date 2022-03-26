package com.platzi.android.rickandmorty

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.platzi.android.rickandmorty.di.DaggerRickAndMortyPlatziComponent
import com.platzi.android.rickandmorty.di.RickAndMortyPlatziComponent

class RickAndMortyPlatziApp : Application() {

    //region Override Methods & Callbacks

    lateinit var component: RickAndMortyPlatziComponent

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        component = initAppComponent()
    }

    //endregion

    private fun initAppComponent() = DaggerRickAndMortyPlatziComponent.factory().create(this)
}
