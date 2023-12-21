package com.example.aplikasigithub.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.aplikasigithub.R
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.database.FavoriteNote
import com.example.aplikasigithub.databinding.ActivityDetailBinding
import com.example.aplikasigithub.ui.insert.FavoriteAddUpdateViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var data: UserItems
    private var isFavorite: Boolean = false
    private lateinit var favUserViewModel: FavoriteAddUpdateViewModel
    private var favUser: FavoriteNote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        val dataJson = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(DetailActivity.MAIN_INTENT_KEY)
        } else {
            @Suppress("DEPRECATION")
            intent.getStringExtra(DetailActivity.MAIN_INTENT_KEY)
        }
        data = Gson().fromJson(dataJson, UserItems::class.java)
        userViewModel.findDetailUser(data.login!!)
        userViewModel.userItems.observe(this){
            setUserData(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, data.login ?: "")

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        userViewModel.isLoading.observe(this){
            showLoading(it)
        }

        favUserViewModel = obtainViewModel(this@DetailActivity)

        favUser = FavoriteNote(namaUser = data.login, avatarUrl = data.avatarUrl)

        favUserViewModel.get(favUser as FavoriteNote).observe(this){
            setIsFavorite(it)
        }

        binding.floatingActionButton.setOnClickListener{
            isFavorite = if (isFavorite){
                Toast.makeText(this, "${data.login} removed from favorite", Toast.LENGTH_SHORT).show()
                favUserViewModel.delete(favUser as FavoriteNote)
                binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this,  R.drawable.baseline_favorite_border_24))
                false

            } else {
                Toast.makeText(this, "${data.login} added to favorite", Toast.LENGTH_SHORT).show()
                favUserViewModel.insert(favUser as FavoriteNote)
                binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this,  R.drawable.baseline_favorite_24))
                true
            }
        }
    }

    private fun setIsFavorite(favUser: FavoriteNote?){
        isFavorite = favUser != null
        if (isFavorite){
            binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = FavoriteUserFactoryViewModel.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteAddUpdateViewModel::class.java]
    }

    private fun setUserData(consumerReviews: UserItems) {
        binding.username.text = consumerReviews.login
        binding.namaasli.text = consumerReviews.name
        binding.noFollowers.text = consumerReviews.followers.toString()
        binding.noFollowing.text = consumerReviews.following.toString()
        Glide.with(binding.root)
            .load(consumerReviews.avatarUrl)
            .into(binding.imageView2)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.noFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.noFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE

        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }



    companion object{
        const val MAIN_INTENT_KEY = "hep"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}

