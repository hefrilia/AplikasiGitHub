package com.example.aplikasigithub.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithub.database.FavoriteNote
import com.example.aplikasigithub.repository.FavoriteNoteRepository

class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavoriteNoteRepository = FavoriteNoteRepository(application)

    fun getAllFavNotes(): LiveData<List<FavoriteNote>> = mFavRepository.getAllFavNotes()

    fun insert(favoriteNote: FavoriteNote) {
        mFavRepository.insert(favoriteNote)
    }

    fun delete(favoriteNote: FavoriteNote) {
        mFavRepository.delete(favoriteNote)
    }

    fun get(favoriteNote: FavoriteNote) : LiveData<FavoriteNote> {
        return mFavRepository.getUser(favoriteNote.namaUser!!)
    }


}