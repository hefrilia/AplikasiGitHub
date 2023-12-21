package com.example.aplikasigithub.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.R
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingViewModel: SettingViewModel
    private var isDarkTheme: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        supportActionBar?.elevation = 0f


        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val userViewModel =ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        userViewModel.UserResponse.observe(this){
            setUserData(it.items)
        }

        userViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)

        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this){
                isDarkMode: Boolean ->
            isDarkTheme = if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false

            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    userViewModel.findUser(searchView.text.toString())
                    false
                }
        }

        with(binding){
            searchBar.inflateMenu(R.menu.menu_main)
            val srcMenu = searchBar.menu
            val favoriteMenuItem = srcMenu.findItem(R.id.item_love)
            val settingMenuItem = srcMenu.findItem(R.id.item_settings)
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES){
                favoriteMenuItem.setIcon(R.drawable.baseline_favorite_white)
                settingMenuItem.setIcon(R.drawable.baseline_settings_white)
            }else{
                favoriteMenuItem.setIcon(R.drawable.baseline_favorite_24)
                settingMenuItem.setIcon(R.drawable.baseline_settings_24)
            }

            favoriteMenuItem.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }
            settingMenuItem.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                true
            }

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnClickListener{
                    showLoading(true)
                    searchBar.text = searchView.text

                    searchView.hide()

                    userViewModel.findUser(searchView.text.toString())
                }

        }

    }

    private fun setUserData(consumerReviews: List<UserItems?>?) {
        val adapter = UserAdapter()
        adapter.submitList(consumerReviews)
        binding.rvUser.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvUser.visibility = if (isLoading) View.GONE else View.VISIBLE

        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}


