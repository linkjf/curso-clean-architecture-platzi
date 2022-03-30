package com.platzi.android.rickandmorty.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.adapters.EpisodeListAdapter
import com.platzi.android.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.imagemanager.bindCircularImageUrl
import com.platzi.android.rickandmorty.parcelable.CharacterParcelable
import com.platzi.android.rickandmorty.parcelable.toCharacterDomain
import com.platzi.android.rickandmorty.presentation.CharacterDetailViewModel
import com.platzi.android.rickandmorty.utils.Constants
import com.platzi.android.rickandmorty.utils.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity() {

    //region Fields
    private lateinit var episodeListAdapter: EpisodeListAdapter
    private lateinit var binding: ActivityCharacterDetailBinding

    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    //endregion

    //region Override Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail)
        binding.lifecycleOwner = this@CharacterDetailActivity

        setUpObservers()

        episodeListAdapter = EpisodeListAdapter { episode ->
            this@CharacterDetailActivity.showLongToast("Episode -> $episode")
        }
        binding.rvEpisodeList.adapter = episodeListAdapter

        characterDetailViewModel.onCharacterValidation(
            intent.getParcelableExtra<CharacterParcelable>(Constants.EXTRA_CHARACTER)?.toCharacterDomain()!!
        )
        binding.characterFavorite.setOnClickListener { characterDetailViewModel.onUpdateFavoriteCharacterStatus() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region Private Methods

    private fun updateCharacterDetails(characterDetail: Character?) {
        characterDetail?.let {
            binding.characterImage.bindCircularImageUrl(
                url = it.image,
                placeholder = R.drawable.ic_camera_alt_black,
                errorPlaceholder = R.drawable.ic_broken_image_black
            )
            binding.characterDataName = it.name
            binding.characterDataStatus = it.status
            binding.characterDataSpecies = it.species
            binding.characterDataGender = it.gender
            binding.characterDataOriginName = it.origin.name
            binding.characterDataLocationName = it.location.name
        } ?: run {
            this@CharacterDetailActivity.showLongToast(R.string.error_no_character_data)
            finish()
            return
        }
    }


    private fun updateFavoriteIcon(isFavorite: Boolean?) {
        binding.characterFavorite.setImageResource(
            if (isFavorite != null && isFavorite) {
                R.drawable.ic_favorite
            } else {
                R.drawable.ic_favorite_border
            }
        )
    }

    private fun setUpObservers() {
        characterDetailViewModel.events.observe(this, Observer { events ->
            events?.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is CharacterDetailViewModel.CharacterDetailNavigation.ShowCharacterDetailsEpisodes -> {
                        episodeListAdapter.updateData(navigation.episodeList)
                    }
                    is CharacterDetailViewModel.CharacterDetailNavigation.ShowCharacterDetailsError -> {
                        this.showLongToast("Error")
                    }
                    CharacterDetailViewModel.CharacterDetailNavigation.ShowLoading -> {
                        binding.episodeProgressBar.isVisible = true
                    }
                    CharacterDetailViewModel.CharacterDetailNavigation.HideLoading -> {
                        binding.episodeProgressBar.isVisible = false
                    }
                }
            }
        })

        characterDetailViewModel.characterData.observe(this, this::updateCharacterDetails)
        characterDetailViewModel.isFavorite.observe(this, this::updateFavoriteIcon)
    }

    //endregion
}
