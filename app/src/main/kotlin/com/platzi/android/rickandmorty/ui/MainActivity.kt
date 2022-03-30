package com.platzi.android.rickandmorty.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.adapters.HomeNavigationStateAdapter
import com.platzi.android.rickandmorty.databinding.ActivityMainBinding
import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.parcelable.toCharacterParcelable
import com.platzi.android.rickandmorty.utils.Constants
import com.platzi.android.rickandmorty.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    CharacterListFragment.OnCharacterListFragmentListener,
    FavoriteListFragment.OnFavoriteListFragmentListener {

    //region Fields
    private lateinit var binding: ActivityMainBinding


    private val homeStatePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.viewPager.currentItem = position
            when (position) {
                0 -> binding.bottomNavigation.menu.findItem(R.id.navigation_list).isChecked = true
                1 -> binding.bottomNavigation.menu.findItem(R.id.navigation_favorites).isChecked = true
            }
        }
    }

    //endregion

    //region Override Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        binding.viewPager.adapter = HomeNavigationStateAdapter(this)
        binding.viewPager.registerOnPageChangeCallback(homeStatePageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(homeStatePageChangeCallback)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_list -> {
                binding.viewPager.currentItem = 0
                true
            }
            R.id.navigation_favorites -> {
                binding.viewPager.currentItem = 1
                true
            }
            else -> false
        }
    }

    override fun openCharacterDetail(character: Character) {
        startActivity<CharacterDetailActivity> {
            putExtra(Constants.EXTRA_CHARACTER, character.toCharacterParcelable())
        }
        overridePendingTransition(R.anim.entry, R.anim.exit)
    }

    override fun openFavoriteCharacterDetail(character: Character) {
        startActivity<CharacterDetailActivity> {
            putExtra(Constants.EXTRA_CHARACTER, character.toCharacterParcelable())
        }
        overridePendingTransition(R.anim.entry, R.anim.exit)
    }


    //endregion
}
