package com.platzi.android.rickandmorty.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.adapters.FavoriteListAdapter
import com.platzi.android.rickandmorty.api.APIConstants.BASE_API_URL
import com.platzi.android.rickandmorty.api.CharacterRequest
import com.platzi.android.rickandmorty.database.CharacterDao
import com.platzi.android.rickandmorty.database.CharacterDatabase
import com.platzi.android.rickandmorty.databinding.FragmentFavoriteListBinding
import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.presentation.FavoriteListViewModel
import com.platzi.android.rickandmorty.usecases.GetAllFavoriteCharactersUseCase
import com.platzi.android.rickandmorty.utils.setItemDecorationSpacing
import kotlinx.android.synthetic.main.fragment_favorite_list.*

class FavoriteListFragment : Fragment() {

    //region Fields


    private lateinit var favoriteListAdapter: FavoriteListAdapter
    private lateinit var listener: OnFavoriteListFragmentListener

    private val characterRequest: CharacterRequest by lazy {
        CharacterRequest(BASE_API_URL)
    }

    private val characterDao: CharacterDao by lazy {
        CharacterDatabase.getDatabase(context?.applicationContext!!).characterDao()
    }

    private val getAllFavoriteCharactersUseCase by lazy {
        GetAllFavoriteCharactersUseCase(characterDao)
    }
    private val favoriteListViewModel: FavoriteListViewModel by lazy {
        FavoriteListViewModel(getAllFavoriteCharactersUseCase)
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnFavoriteListFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnFavoriteListFragmentListener")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        return DataBindingUtil.inflate<FragmentFavoriteListBinding>(
                inflater,
                R.layout.fragment_favorite_list,
                container,
                false
        ).apply {
            lifecycleOwner = this@FavoriteListFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteListAdapter = FavoriteListAdapter { character ->
            listener.openFavoriteCharacterDetail(character)
        }
        favoriteListAdapter.setHasStableIds(true)

        rvFavoriteList.run {
            setItemDecorationSpacing(resources.getDimension(R.dimen.list_item_padding))
            adapter = favoriteListAdapter
        }
        setUpObservers()
    }

    //endregion

    //region Private Methods

    private fun setUpObservers() {
        favoriteListViewModel.favoriteCharacterList.observe(viewLifecycleOwner, Observer(favoriteListViewModel::onFavoriteCharacterList))
        favoriteListViewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is FavoriteListViewModel.FavoriteListNavigation.ShowCharacterList -> {
                        tvEmptyListMessage.isVisible = false
                        favoriteListAdapter.updateData(navigation.characterList)
                    }
                    FavoriteListViewModel.FavoriteListNavigation.ShowEmptyList -> {
                        tvEmptyListMessage.isVisible = true
                        favoriteListAdapter.updateData(emptyList())
                    }
                }
            }
        })
    }

    //endregion

    //region Inner Classes & Interfaces

    interface OnFavoriteListFragmentListener {
        fun openFavoriteCharacterDetail(character: Character)
    }

    //endregion

    //region Companion object

    companion object {

        fun newInstance(args: Bundle? = Bundle()) = FavoriteListFragment().apply {
            arguments = args
        }
    }

    //endregion

}
