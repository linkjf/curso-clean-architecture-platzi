package com.platzi.android.rickandmorty.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.adapters.FavoriteListAdapter
import com.platzi.android.rickandmorty.databinding.FragmentFavoriteListBinding
import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.presentation.FavoriteListViewModel
import com.platzi.android.rickandmorty.utils.setItemDecorationSpacing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {

    //region Fields

    private lateinit var _binding: FragmentFavoriteListBinding
    private lateinit var favoriteListAdapter: FavoriteListAdapter
    private lateinit var listener: OnFavoriteListFragmentListener

    private val favoriteListViewModel: FavoriteListViewModel by viewModels<FavoriteListViewModel>()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite_list,
            container,
            false
        )
        _binding.lifecycleOwner = this@FavoriteListFragment
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteListAdapter = FavoriteListAdapter { character ->
            listener.openFavoriteCharacterDetail(character)
        }
        favoriteListAdapter.setHasStableIds(true)

        _binding.rvFavoriteList.run {
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
                        _binding.tvEmptyListMessage.isVisible = false
                        favoriteListAdapter.updateData(navigation.characterList)
                    }
                    FavoriteListViewModel.FavoriteListNavigation.ShowEmptyList -> {
                        _binding.tvEmptyListMessage.isVisible = true
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
