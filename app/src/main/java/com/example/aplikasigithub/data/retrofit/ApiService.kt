package com.example.aplikasigithub.data.retrofit

import com.example.aplikasigithub.data.response.UserItems
import com.example.aplikasigithub.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getListUser (
        @Query("q") user : String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUserResponse(
        @Path("username") username: String
    ): Call<UserItems>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String
    ): Call<List<UserItems>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String
    ): Call<List<UserItems>>
}