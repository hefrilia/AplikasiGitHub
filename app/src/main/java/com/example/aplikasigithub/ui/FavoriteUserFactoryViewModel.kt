package com.example.aplikasigithub.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithub.ui.insert.FavoriteAddUpdateViewModel
import com.example.aplikasigithub.ui.main.MainFavoriteUserViewModel

class FavoriteUserFactoryViewModel private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserFactoryViewModel? = null
        @JvmStatic
        fun getInstance(application: Application): FavoriteUserFactoryViewModel {
            if (INSTANCE == null) {
                synchronized(FavoriteUserFactoryViewModel::class.java) {
                    INSTANCE = FavoriteUserFactoryViewModel(application)
                }
            }
            return INSTANCE as FavoriteUserFactoryViewModel
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFavoriteUserViewModel::class.java)) {
            return MainFavoriteUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddUpdateViewModel::class.java)) {
            return FavoriteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}