package com.example.aplikasigithub.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.aplikasigithub.database.FavoriteNote

class FavoriteDiffCallback (private val oldFavList: List<FavoriteNote>, private val newFavList: List<FavoriteNote>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.id == newFav.id && oldFav.namaUser== newFav.namaUser
    }
}