package com.platzi.android.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.databinding.ItemGridCharacterBinding
import com.platzi.android.rickandmorty.domain.Entities.Character
import com.platzi.android.rickandmorty.imagemanager.bindImageUrl


class CharacterGridAdapter(
    private val listener: (Character) -> Unit
) : RecyclerView.Adapter<CharacterGridAdapter.CharacterGridViewHolder>() {

    private val characterList: MutableList<Character> = mutableListOf()

    fun addData(newData: List<Character>) {
        characterList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterGridViewHolder {
        val itemBinding = ItemGridCharacterBinding.inflate(LayoutInflater.from(parent.context))
        return CharacterGridViewHolder(itemBinding, listener)
    }

    override fun getItemCount() = characterList.size

    override fun getItemId(position: Int): Long = characterList[position].id.toLong()

    override fun onBindViewHolder(holder: CharacterGridViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    class CharacterGridViewHolder(
        private val dataBinding: ItemGridCharacterBinding,
        private val listener: (Character) -> Unit
    ) : RecyclerView.ViewHolder(dataBinding.root) {

        //region Public Methods
        fun bind(item: Character) {
            dataBinding.character = item
            dataBinding.characterImage.bindImageUrl(
                url = item.image,
                placeholder = R.drawable.ic_camera_alt_black,
                errorPlaceholder = R.drawable.ic_broken_image_black
            )
            itemView.setOnClickListener { listener(item) }
        }

    }
}
