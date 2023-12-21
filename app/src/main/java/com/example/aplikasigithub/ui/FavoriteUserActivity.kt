package com.example.aplikasigithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.database.FavoriteNote
import com.example.aplikasigithub.databinding.ActivityFavoriteUserBinding
import com.example.aplikasigithub.ui.main.MainFavoriteUserViewModel

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var favUserAdapter: FavoriteUserAdapter
    private  lateinit var binding: ActivityFavoriteUserBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


       binding.btnLovedBack.setOnClickListener{
           onBackPressed()
       }

        val mainViewModel = obtainViewModel(this@FavoriteUserActivity)

        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                favUserAdapter.setListNotes(noteList)
            }
        }

        favUserAdapter = FavoriteUserAdapter()
        binding.rvFavUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavUser.setHasFixedSize(true)
        binding.rvFavUser.adapter = favUserAdapter

        favUserAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun OnItemClicked(data: FavoriteNote) {
                mainViewModel.deleteFavoriteUser(data)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainFavoriteUserViewModel {
        val factory = FavoriteUserFactoryViewModel.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainFavoriteUserViewModel::class.java]
    }

}