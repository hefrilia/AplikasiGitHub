package com.example.aplikasigithub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [FavoriteNote::class], version = 1)
abstract class FavoriteNoteRoomDatabase : RoomDatabase() {
    abstract fun favoriteNoteDao(): FavoriteNoteDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteNoteRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteNoteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteNoteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteNoteRoomDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteNoteRoomDatabase
        }
    }
}