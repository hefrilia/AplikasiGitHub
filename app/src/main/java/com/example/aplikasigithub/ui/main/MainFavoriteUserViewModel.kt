package com.example.aplikasigithub.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithub.database.FavoriteNote
import com.example.aplikasigithub.repository.FavoriteNoteRepository

class MainFavoriteUserViewModel (application: Application) : ViewModel() {
    private val mFavoriteNoteRepository: FavoriteNoteRepository = FavoriteNoteRepository(application)

    fun getAllNotes(): LiveData<List<FavoriteNote>> = mFavoriteNoteRepository.getAllFavNotes()

    fun deleteFavoriteUser(favoriteNote: FavoriteNote) = mFavoriteNoteRepository.delete(favoriteNote)
}