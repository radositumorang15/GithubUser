package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.FollowResponse
import com.dicoding.githubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") q: String
    ): Call<GithubResponse>



    @GET("users")
    fun getUsers(@Query("q") q: String): Call<List<GithubResponse>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowResponse>>
}