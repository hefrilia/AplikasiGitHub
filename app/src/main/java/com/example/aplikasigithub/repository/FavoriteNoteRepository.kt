package com.example.aplikasigithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.aplikasigithub.database.FavoriteNote
import com.example.aplikasigithub.database.FavoriteNoteDao
import com.example.aplikasigithub.database.FavoriteNoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteNoteRepository(application: Application) {
    private val mFavoriteDao: FavoriteNoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteNoteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteNoteDao()
    }
    fun getAllFavNotes(): LiveData<List<FavoriteNote>> = mFavoriteDao.getAllFavNotes()

    fun insert(favoriteNote: FavoriteNote) {
        executorService.execute { mFavoriteDao.insert(favoriteNote) }
    }

    fun delete(favoriteNote: FavoriteNote) {
        executorService.execute { mFavoriteDao.delete(favoriteNote.namaUser!!) }
    }

    fun getUser(getUser: String) : LiveData<FavoriteNote> = mFavoriteDao.getUser(getUser)
}
