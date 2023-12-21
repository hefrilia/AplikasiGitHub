package com.example.aplikasigithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.data.response.UserResponse
import com.example.aplikasigithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class UserViewModel : ViewModel() {
    private val _userresponse = MutableLiveData<UserResponse>()
    val UserResponse: LiveData<UserResponse> = _userresponse

    private val _useritems = MutableLiveData<UserItems>()
    val userItems: LiveData<UserItems> = _useritems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UserViewModel"
        private const val  DefaultName = "A"
    }

    init {
        findUser()
    }

    fun findDetailUser(userArg: String = DefaultName) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUserResponse(userArg)
        client.enqueue(object : Callback<UserItems> {
            override fun onResponse(
                call: Call<UserItems>,
                response: Response<UserItems>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _useritems.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserItems>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findUser(userArg: String = DefaultName) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser(userArg)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userresponse.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
