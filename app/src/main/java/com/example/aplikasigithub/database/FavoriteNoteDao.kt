package com.example.aplikasigithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteNoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteNote: FavoriteNote)

    @Query("DELETE FROM favoritenote WHERE user = :namaUser")
    fun delete(namaUser: String)

    @Query("SELECT * from favoritenote ORDER BY id ASC")
    fun getAllFavNotes(): LiveData<List<FavoriteNote>>

    @Query("SELECT * from favoritenote WHERE user = :namaUser")
    fun getUser(namaUser: String): LiveData<FavoriteNote>
}