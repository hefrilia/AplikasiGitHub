package com.example.aplikasigithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowersModel: ViewModel() {

    private val _followers = MutableLiveData<List<UserItems>>()
    val followers: LiveData<List<UserItems>> = _followers

    private val _following = MutableLiveData<List<UserItems>>()
    val following: LiveData<List<UserItems>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UserFollowersModel"
    }


    fun findFollowers(userArg: String, isFollowing: Boolean = true) {
        _isLoading.value = true
        val client : Call<List<UserItems>> = if (isFollowing){
            ApiConfig.getApiService().getFollowers(userArg)
        }else{
            ApiConfig.getApiService().getFollowers(userArg)
        }
        client.enqueue(object : Callback<List<UserItems>>  {
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    fun findFollowing(userArg: String, isFollowing: Boolean = true) {
        _isLoading.value = true
        val client : Call<List<UserItems>> = if (isFollowing){
            ApiConfig.getApiService().getFollowing(userArg)
        }else{
            ApiConfig.getApiService().getFollowing(userArg)
        }
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}